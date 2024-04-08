package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.ReservaDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;
    private final ClienteService clienteService;
    private final HotelService hotelService;
    private final FuncionarioService funcionarioService;
    private final StatusReservaService statusreservaService;
    private final TipoQuartoReservaService tipoQuartoReservaService;

    @GetMapping()
    public ResponseEntity get() {
       List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                reserva.setCliente(null);
            } else {
                reserva.setCliente(cliente.get());
            }
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                reserva.setHotel(null);
            } else {
                reserva.setHotel(hotel.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                reserva.setFuncionario(null);
            } else {
                reserva.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdStatusReserva() != null) {
            Optional<StatusReserva> statusreserva = statusreservaService.getStatusReservaById(dto.getIdStatusReserva());
            if (!statusreserva.isPresent()) {
                reserva.setStatusReserva(null);
            } else {
                reserva.setStatusReserva(statusreserva.get());
            }
        }
         if (dto.getIdTipoQuartoReserva() != null) {
             Optional<TipoQuartoReserva> tipoQuartoReserva = tipoQuartoReservaService.getTipoQuartoReservaById(dto.getIdTipoQuartoReserva());
            if (!tipoQuartoReserva.isPresent()) {
                reserva.setTipoQuartoReserva(null);
            } else {
                reserva.setTipoQuartoReserva(tipoQuartoReserva.get());
             }
         }
        return reserva;
    }
}