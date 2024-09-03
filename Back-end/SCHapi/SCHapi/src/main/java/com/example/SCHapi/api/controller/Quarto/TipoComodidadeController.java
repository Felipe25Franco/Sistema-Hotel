package com.example.SCHapi.api.controller.Quarto;

import com.example.SCHapi.api.dto.Quarto.TipoComodidadeDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.service.Quarto.TipoComodidadeService;

import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoComodidades")
@RequiredArgsConstructor
@Api("API de Tipo Comodidade")
@CrossOrigin
public class TipoComodidadeController {
    private final TipoComodidadeService service;

    @GetMapping()
    @ApiOperation("Obter a lista de tipo de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Tipo de Comodidade retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Tipo de Comodidade não encontrado")
    })
    public ResponseEntity get() {
       List<TipoComodidade> tipoComodidades = service.getTipoComodidades();
        return ResponseEntity.ok(tipoComodidades.stream().map(TipoComodidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um tipo de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Comodidade encontrado"),
            @ApiResponse(code  = 404, message  = "Tipo de Comodidade não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Tipo Comodidade") Long id) {
        Optional<TipoComodidade> tipoComodidade = service.getTipoComodidadeById(id);
        if (!tipoComodidade.isPresent()) {
            return new ResponseEntity("TipoComodidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoComodidade.map(TipoComodidadeDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um tipo de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Tipo de Comodidade salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Tipo de Comodidade"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody TipoComodidadeDTO dto) {
        try {
            TipoComodidade tipoComodidade = converter(dto);
            tipoComodidade = service.salvar(tipoComodidade);
            return new ResponseEntity(tipoComodidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Comodidade alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Comodidade não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoComodidadeDTO dto) {
        if (!service.getTipoComodidadeById(id).isPresent()) {
            return new ResponseEntity("TipoComodidade não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoComodidade tipoComodidade = converter(dto);
            tipoComodidade.setId(id);
            System.out.println(dto);
            service.salvar(tipoComodidade);
            return ResponseEntity.ok(tipoComodidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de comodidade")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Tipo de Comodidade excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Comodidade não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<TipoComodidade> tipoComodidade = service.getTipoComodidadeById(id);
        if (!tipoComodidade.isPresent()) {
            return new ResponseEntity("Tipo de comodidade não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoComodidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoComodidade converter(TipoComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoComodidade.class);
    }
}