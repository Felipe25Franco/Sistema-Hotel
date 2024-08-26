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

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/statusReservas")
@RequiredArgsConstructor
@CrossOrigin
public class StatusReservaController {
    
    private final StatusReservaService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de status de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Status de Reserva retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Status de Reserva não encontrado")
    // })
    public ResponseEntity get() {
       List<StatusReserva> statusReservas = service.getStatusReserva();
        return ResponseEntity.ok(statusReservas.stream().map(StatusReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um status de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Reserva encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusReserva> statusReserva = service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("Status de Reserva não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusReserva.map(StatusReservaDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um status de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Status de Reserva salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Status de Reserva"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um status de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Reserva alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um status de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Status de Reserva excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
