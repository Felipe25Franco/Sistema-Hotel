package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.StatusQuartoDTO;
import com.example.SCHapi.api.dto.StatusReservaDTO;
import com.example.SCHapi.model.entity.StatusQuarto;
import com.example.SCHapi.model.entity.StatusReserva;
import com.example.SCHapi.service.StatusQuartoService;
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
@RequestMapping("/api/v1/statusQuartos")
@RequiredArgsConstructor
public class StatusQuartoController {
    private final StatusQuartoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<StatusQuarto> statusQuartos = service.getStatusQuarto();
        return ResponseEntity.ok(statusQuartos.stream().map(StatusQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusQuarto> statusQuarto = service.getStatusQuartoById(id);
        if (!statusQuarto.isPresent()) {
            return new ResponseEntity("statusQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusQuarto.map(StatusQuartoDTO::create));
    }

    public StatusQuarto converter(StatusQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusQuarto.class);
    }
}
