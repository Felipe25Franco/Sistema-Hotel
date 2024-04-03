package com.example.SCHapi.api.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.TipoQuartoDTO;
import com.example.SCHapi.model.entity.TipoQuarto;
import com.example.SCHapi.service.TipoQuartoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipoQuartos")
@RequiredArgsConstructor
public class TipoQuartoController {

    private final TipoQuartoService service;

    @GetMapping()
    public ResponseEntity get() {
       List<TipoQuarto> tipoQuartos = service.getTipoQuartos();
        return ResponseEntity.ok(tipoQuartos.stream().map(TipoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoQuarto> tipoQuarto = service.getTipoQuartoById(id);
        if (!tipoQuarto.isPresent()) {
            return new ResponseEntity("TipoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoQuarto.map(TipoQuartoDTO::create));
    }

    public TipoQuarto converter(TipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoQuarto.class);
    }
}