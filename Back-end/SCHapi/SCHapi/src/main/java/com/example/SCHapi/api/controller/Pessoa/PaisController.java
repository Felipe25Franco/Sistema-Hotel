package com.example.SCHapi.api.controller.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Pessoa.PaisDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Pais;
import com.example.SCHapi.service.Pessoa.PaisService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/paises")
@RequiredArgsConstructor
@Api("API de Pais")
@CrossOrigin
public class PaisController {
    
    private final PaisService service;

    @GetMapping()
    @ApiOperation("Obter a lista de país")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de País retornada com sucesso"),
            @ApiResponse(code  = 500,  message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "País não encontrado")
    })
    public ResponseEntity get() {
        List<Pais> paises = service.getPaises(); 
        return ResponseEntity.ok(paises.stream().map(PaisDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um país")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "País encontrado"),
            @ApiResponse(code  = 404, message  = "País não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pais> pais = service.getPaisById(id);
        if (!pais.isPresent()) {
            return new ResponseEntity("Pais não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pais.map(PaisDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um país")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "País salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o País"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody PaisDTO dto) {
        try {
            Pais pais = converter(dto);
            pais = service.salvar(pais);
            return new ResponseEntity(pais, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um país")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "País alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "País não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody PaisDTO dto) {
        if (!service.getPaisById(id).isPresent()) {
            return new ResponseEntity("Pais não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Pais pais = converter(dto);
            pais.setId(id);
            System.out.println(dto);
            service.salvar(pais);
            return ResponseEntity.ok(pais);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um país")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "País excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "País não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Pais> pais = service.getPaisById(id);
        if (!pais.isPresent()) {
            return new ResponseEntity("Pais não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pais.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Pais converter(PaisDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Pais.class);
    }
}
