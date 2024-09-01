package com.example.SCHapi.api.controller.Estadia;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Estadia.StatusReservaDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.StatusReserva;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.service.Estadia.StatusReservaService;

import io.swagger.annotations.*;

@RestController
@RequestMapping("/api/v1/statusReservas")
@RequiredArgsConstructor
@Api("API de Status Reserva")
@CrossOrigin
public class StatusReservaController {
    
    private final StatusReservaService service;

    @GetMapping()
    @ApiOperation("Obter a lista de status de reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Status de Reserva retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Status de Reserva não encontrado")
    })
    public ResponseEntity get() {
       List<StatusReserva> statusReservas = service.getStatusReserva();
        return ResponseEntity.ok(statusReservas.stream().map(StatusReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um status de reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Reserva encontrado"),
            @ApiResponse(code  = 404, message  = "Status de Reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusReserva> statusReserva = service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("Status de Reserva não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusReserva.map(StatusReservaDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um status de reserva")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Status de Reserva salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Status de Reserva"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody StatusReservaDTO dto) {
        try {
            StatusReserva statusReserva = converter(dto);
            statusReserva = service.salvar(statusReserva);
            return new ResponseEntity(statusReserva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um status de reserva")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Reserva alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody StatusReservaDTO dto) {
        if (!service.getStatusReservaById(id).isPresent()) {
            return new ResponseEntity("StatusReserva não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            StatusReserva statusReserva = converter(dto);
            statusReserva.setId(id);
            System.out.println(dto);
            service.salvar(statusReserva);
            return ResponseEntity.ok(statusReserva);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um status de reserva")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Status de Reserva excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Reserva não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<StatusReserva> statusReserva = service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("Status de Reserva não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(statusReserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public StatusReserva converter(StatusReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusReserva.class);
    }
}
