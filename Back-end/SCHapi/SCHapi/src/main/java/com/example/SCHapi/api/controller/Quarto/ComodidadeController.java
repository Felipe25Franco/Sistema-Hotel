package com.example.SCHapi.api.controller.Quarto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Quarto.ComodidadeDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.service.Quarto.ComodidadeService;
import com.example.SCHapi.service.Quarto.TipoComodidadeService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comodidades")
@RequiredArgsConstructor
@Api("API de Comodidade")
@CrossOrigin
public class ComodidadeController {

    private final ComodidadeService service;
    private final TipoComodidadeService tipoComodidadeService;
    
    @GetMapping()
    @ApiOperation("Obter a lista de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Comodidade retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Comodidade não encontrado")
    })
    public ResponseEntity get() {
       List<Comodidade> comodidades = service.getComodidades();
        return ResponseEntity.ok(comodidades.stream().map(ComodidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Comodidade encontrado"),
            @ApiResponse(code  = 404, message  = "Comodidade não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Comodidade> comodidade = service.getComodidadeById(id);
        if (!comodidade.isPresent()) {
            return new ResponseEntity("Comodidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comodidade.map(ComodidadeDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um comodidade")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Comodidade salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Comodidade"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody ComodidadeDTO dto) {
        try {
            Comodidade comodidade = converter(dto);
            comodidade = service.salvar(comodidade);
            return new ResponseEntity(comodidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Comodidade alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Comodidade não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ComodidadeDTO dto) {
        if (!service.getComodidadeById(id).isPresent()) {
            return new ResponseEntity("Comodidade não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Comodidade comodidade = converter(dto);
            comodidade.setId(id);
            System.out.println(dto);
            service.salvar(comodidade);
            return ResponseEntity.ok(comodidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    @ApiOperation("Exclui um comodidade")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Comodidade excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Comodidade não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Comodidade> comodidade = service.getComodidadeById(id);
        if (!comodidade.isPresent()) {
            return new ResponseEntity("Comodidade não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(comodidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Comodidade converter(ComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Comodidade comodidade = modelMapper.map(dto, Comodidade.class);
        if (dto.getIdTipoComodidade() != null) {
            Optional<TipoComodidade> tipoComodidade = tipoComodidadeService.getTipoComodidadeById(dto.getIdTipoComodidade());
            if (!tipoComodidade.isPresent()) {
                comodidade.setTipoComodidade(null);
            } else {
                comodidade.setTipoComodidade(tipoComodidade.get());
            }
        }
        return comodidade;
    }
}