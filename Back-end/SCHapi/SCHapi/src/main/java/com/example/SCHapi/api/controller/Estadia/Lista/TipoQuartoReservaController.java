package com.example.SCHapi.api.controller.Estadia.Lista;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.service.Estadia.ReservaService;
import com.example.SCHapi.service.Estadia.Lista.TipoQuartoReservaService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipoQuartoReservas")
@RequiredArgsConstructor
@Api("API de Tipo Quarto Reserva")
@CrossOrigin
public class TipoQuartoReservaController {

    private final TipoQuartoReservaService service;
    private final ReservaService reservaService;
    private final TipoQuartoService tipoQuartoService;

    @GetMapping()
    @ApiOperation("Obter a lista de tipo de quarto de uma reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Tipo de Quarto de uma reserva retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Tipo de Quarto de uma reserva não encontrado")
    })
    public ResponseEntity get() {
       List<TipoQuartoReserva> tipoQuartoReservas = service.getTipoQuartoReservas();
        return ResponseEntity.ok(tipoQuartoReservas.stream().map(TipoQuartoReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um tipo de quarto de uma reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Quarto de uma reserva encontrado"),
            @ApiResponse(code  = 404, message  = "Tipo de Quarto de uma reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Tipo Quarto Reserva")  Long id) {
        Optional<TipoQuartoReserva> tipoQuartoReserva = service.getTipoQuartoReservaById(id);
        if (!tipoQuartoReserva.isPresent()) {
            return new ResponseEntity("TipoQuartoReserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoQuartoReserva.map(TipoQuartoReservaDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um tipo de quarto de uma reserva")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Tipo de Quarto de uma reserva salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Tipo de Quarto de uma reserva"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody TipoQuartoReservaDTO dto) {
        try {
            TipoQuartoReserva tipoQuartoReserva = converter(dto);
            tipoQuartoReserva = service.salvar(tipoQuartoReserva);
            return new ResponseEntity(tipoQuartoReserva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de quarto de uma reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Quarto de uma reserva alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Quarto de uma reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody TipoQuartoReservaDTO dto) {
        if (!service.getTipoQuartoReservaById(id).isPresent()) {
            return new ResponseEntity("TipoQuartoReserva não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoQuartoReserva tipoQuartoReserva = converter(dto);
            tipoQuartoReserva.setId(id);
            System.out.println(dto);
            service.salvar(tipoQuartoReserva);
            return ResponseEntity.ok(tipoQuartoReserva);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de quarto de uma reserva")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Tipo de Quarto de uma reserva excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Quarto de uma reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoQuartoReserva> tipoQuartoReserva = service.getTipoQuartoReservaById(id);
        if (!tipoQuartoReserva.isPresent()) {
            return new ResponseEntity("TipoQuartoReserva não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoQuartoReserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoQuartoReserva converter(TipoQuartoReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoReserva tipoQuartoReserva = modelMapper.map(dto, TipoQuartoReserva.class);
        if (dto.getIdReserva() != null) {
            Optional<Reserva> reserva = reservaService.getReservaById(dto.getIdReserva());
            if (!reserva.isPresent()) {
                tipoQuartoReserva.setReserva(null);
            } else {
                tipoQuartoReserva.setReserva(reserva.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                tipoQuartoReserva.setTipoQuarto(null);
            } else {
                tipoQuartoReserva.setTipoQuarto(tipoQuarto.get());
            }
        }
        return tipoQuartoReserva;
    }
}