package com.example.SCHapi.api.controller.Quarto.Lista;

import com.example.SCHapi.api.dto.Quarto.Lista.TipoCamaTipoQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Quarto.TipoCamaService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;
import com.example.SCHapi.service.Quarto.Lista.TipoCamaTipoQuartoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoCamaTipoQuartos")
@RequiredArgsConstructor
@Api("API de Tipo Cama Tipo Quarto")
@CrossOrigin
public class TipoCamaTipoQuartoController {

    private final TipoCamaTipoQuartoService service;
    private final TipoCamaService tipoCamaService;
    private final TipoQuartoService tipoQuartoService;

    @GetMapping()
    @ApiOperation("Obter a lista de tipo de cama de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Tipo de Cama de um Tipo de Quarto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama de um Tipo de Quarto não encontrado")
    })
    public ResponseEntity get() {
       List<TipoCamaTipoQuarto> tipoCamaTipoQuartos = service.getTipoCamaTipoQuartos();
        return ResponseEntity.ok(tipoCamaTipoQuartos.stream().map(TipoCamaTipoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um tipo de cama de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Cama de um Tipo de Quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Tipo Cama Tipo Quarto")  Long id) {
        Optional<TipoCamaTipoQuarto> tipoCamaTipoQuarto = service.getTipoCamaTipoQuartoById(id);
        if (!tipoCamaTipoQuarto.isPresent()) {
            return new ResponseEntity("TipoCamaTipoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoCamaTipoQuarto.map(TipoCamaTipoQuartoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um tipo de cama de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Tipo de Cama de um Tipo de Quarto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Tipo de Cama de um Tipo de Quarto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody TipoCamaTipoQuartoDTO dto) {
        try {
            TipoCamaTipoQuarto tipoCamaTipoQuarto = converter(dto);
            tipoCamaTipoQuarto = service.salvar(tipoCamaTipoQuarto);
            return new ResponseEntity(tipoCamaTipoQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de cama de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Cama de um Tipo de Quarto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoCamaTipoQuartoDTO dto) {
        if (!service.getTipoCamaTipoQuartoById(id).isPresent()) {
            return new ResponseEntity("TipoCamaTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoCamaTipoQuarto tipoCamaTipoQuarto = converter(dto);
            tipoCamaTipoQuarto.setId(id);
            System.out.println(dto);
            service.salvar(tipoCamaTipoQuarto);
            return ResponseEntity.ok(tipoCamaTipoQuarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de cama de um tipo de quarto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Tipo de Cama de um Tipo de Quarto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama de um Tipo de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<TipoCamaTipoQuarto> tipoCamaTipoQuarto = service.getTipoCamaTipoQuartoById(id);
        if (!tipoCamaTipoQuarto.isPresent()) {
            return new ResponseEntity("TipoCamaTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoCamaTipoQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoCamaTipoQuarto converter(TipoCamaTipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoCamaTipoQuarto tipoCamaTipoQuarto = modelMapper.map(dto, TipoCamaTipoQuarto.class);
        if (dto.getIdTipoCama() != null) {
            Optional<TipoCama> tipoCama = tipoCamaService.getTipoCamaById(dto.getIdTipoCama());
            if (!tipoCama.isPresent()) {
                tipoCamaTipoQuarto.setTipoCama(null);
            } else {
                tipoCamaTipoQuarto.setTipoCama(tipoCama.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                tipoCamaTipoQuarto.setTipoQuarto(null);
            } else {
                tipoCamaTipoQuarto.setTipoQuarto(tipoQuarto.get());
            }
        }
        return tipoCamaTipoQuarto;
    }
}