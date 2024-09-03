package com.example.SCHapi.api.controller.Quarto.Lista;

import com.example.SCHapi.api.dto.Quarto.Lista.ComodidadeTipoQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Quarto.ComodidadeService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;
import com.example.SCHapi.service.Quarto.Lista.ComodidadeTipoQuartoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comodidadeTipoQuartos")
@RequiredArgsConstructor
@Api("API de Comodidade Tipo Quarto")
@CrossOrigin
public class ComodidadeTipoQuartoController {

    private final ComodidadeTipoQuartoService service;
    private final ComodidadeService comodidadeService;
    private final TipoQuartoService tipoQuartoService;

    @GetMapping()
    @ApiOperation("Obter a lista de comodidade de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Comodidade de um Tipo de Quarto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Comodidade de um Tipo de Quarto não encontrado")
    })
    public ResponseEntity get() {
       List<ComodidadeTipoQuarto> comodidadeTipoQuartos = service.getComodidadeTipoQuartos();
        return ResponseEntity.ok(comodidadeTipoQuartos.stream().map(ComodidadeTipoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um comodidade de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Comodidade de um Tipo de Quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Comodidade de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Comodidade Tipo Quarto")  Long id) {
        Optional<ComodidadeTipoQuarto> comodidadeTipoQuarto = service.getComodidadeTipoQuartoById(id);
        if (!comodidadeTipoQuarto.isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comodidadeTipoQuarto.map(ComodidadeTipoQuartoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um comodidade de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Comodidade de um Tipo de Quarto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Comodidade de um Tipo de Quarto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody ComodidadeTipoQuartoDTO dto) {
        try {
            ComodidadeTipoQuarto comodidadeTipoQuarto = converter(dto);
            comodidadeTipoQuarto = service.salvar(comodidadeTipoQuarto);
            return new ResponseEntity(comodidadeTipoQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um comodidade de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Comodidade de um Tipo de Quarto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Comodidade de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody ComodidadeTipoQuartoDTO dto) {
        if (!service.getComodidadeTipoQuartoById(id).isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ComodidadeTipoQuarto comodidadeTipoQuarto = converter(dto);
            comodidadeTipoQuarto.setId(id);
            System.out.println(dto);
            service.salvar(comodidadeTipoQuarto);
            return ResponseEntity.ok(comodidadeTipoQuarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um comodidade de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Comodidade de um Tipo de Quarto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Comodidade de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ComodidadeTipoQuarto> comodidadeTipoQuarto = service.getComodidadeTipoQuartoById(id);
        if (!comodidadeTipoQuarto.isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(comodidadeTipoQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ComodidadeTipoQuarto converter(ComodidadeTipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ComodidadeTipoQuarto comodidadeTipoQuarto = modelMapper.map(dto, ComodidadeTipoQuarto.class);
        if (dto.getIdComodidade() != null) {
            Optional<Comodidade> comodidade = comodidadeService.getComodidadeById(dto.getIdComodidade());
            if (!comodidade.isPresent()) {
                comodidadeTipoQuarto.setComodidade(null);
            } else {
                comodidadeTipoQuarto.setComodidade(comodidade.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                comodidadeTipoQuarto.setTipoQuarto(null);
            } else {
                comodidadeTipoQuarto.setTipoQuarto(tipoQuarto.get());
            }
        }
        return comodidadeTipoQuarto;
    }
}