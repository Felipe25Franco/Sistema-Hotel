package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.StatusHospedagemDTO;
import com.example.SCHapi.api.dto.StatusReservaDTO;
import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.entity.StatusReserva;
import com.example.SCHapi.service.StatusHospedagemService;
import com.example.SCHapi.service.StatusReservaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/statusReservas")
@RequiredArgsConstructor
public class StatusReservaController {
    private final StatusReservaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<StatusReserva> statusReservas = service.getStatusReserva();
        return ResponseEntity.ok(statusReservas.stream().map(StatusReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusReserva> statusReserva = service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("statusReserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusReserva.map(StatusReservaDTO::create));
    }

    public StatusReserva converter(StatusReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusReserva.class);
    }
}
