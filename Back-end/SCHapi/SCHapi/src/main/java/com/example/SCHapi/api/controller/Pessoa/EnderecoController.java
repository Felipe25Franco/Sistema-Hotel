package com.example.SCHapi.api.controller.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Pessoa.EnderecoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.service.Pessoa.EnderecoService;
import com.example.SCHapi.service.Pessoa.UfService;

import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
@Api("API de Endereço")
@CrossOrigin
public class EnderecoController {
    
    private final EnderecoService service;
    private final UfService ufService;

    @GetMapping()
    @ApiOperation("Obter a lista de endereço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Endereço retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Endereço não encontrado")
    })
    public ResponseEntity get() {
       List<Endereco> enderecos = service.getEnderecos();
        return ResponseEntity.ok(enderecos.stream().map(EnderecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um endereço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Endereço encontrado"),
            @ApiResponse(code  = 404, message  = "Endereço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Endereço")  Long id) {
        Optional<Endereco> endereco = service.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(endereco.map(EnderecoDTO::create));
    }
    @PostMapping
    @ApiOperation("Salva um endereço")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Endereço salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Endereço"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody EnderecoDTO dto) {
        try {
            Endereco endereco = converter(dto);
            endereco = service.salvar(endereco);
            return new ResponseEntity(endereco, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um endereço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Endereço alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Endereço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EnderecoDTO dto) {
        if (!service.getEnderecoById(id).isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Endereco endereco = converter(dto);
            endereco.setId(id);
            System.out.println(dto);
            service.salvar(endereco);
            return ResponseEntity.ok(endereco);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um endereço")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Endereço excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Endereço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Endereco> endereco = service.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(endereco.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Endereco converter(EnderecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        if (dto.getIdUf() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            if (!uf.isPresent()) {
                endereco.setUf(null);
            } else {
                endereco.setUf(uf.get());
            }
        }
        return endereco;
    }

}
