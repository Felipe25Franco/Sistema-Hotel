package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.ReservaDTO;
import com.example.SCHapi.model.entity.Reserva;
import com.example.SCHapi.model.entity.Cliente;
import com.example.SCHapi.model.entity.Funcionario;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.model.entity.StatusReserva;
import com.example.SCHapi.service.ClienteService;
import com.example.SCHapi.service.FuncionarioService;
import com.example.SCHapi.service.ReservaService;
import com.example.SCHapi.service.HotelService;
import com.example.SCHapi.service.StatusReservaService;
import com.example.SCHapi.service.TipoQuartoService;

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
    private final TipoQuartoService tipoquartoService;

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
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        StatusReserva statusReserva = modelMapper.map(dto, StatusReserva.class);
        return reserva;
    }
}