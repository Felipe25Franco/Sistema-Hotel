package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.AvaliacaoQuartoDTO;
import com.example.SCHapi.model.entity.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.TipoQuarto;
import com.example.SCHapi.service.AvaliacaoQuartoService;
import com.example.SCHapi.service.TipoQuartoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/avaliacaoQuartos")
@RequiredArgsConstructor
public class AvaliacaoQuartoController {

    private final AvaliacaoQuartoService service;
    private final TipoQuartoService tipoQuartoService;
    
    @GetMapping()
    public ResponseEntity get() {
       List<AvaliacaoQuarto> avaliacaoQuartos = service.getAvaliacaoQuartos();
        return ResponseEntity.ok(avaliacaoQuartos.stream().map(AvaliacaoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AvaliacaoQuarto> avaliacaoQuarto = service.getAvaliacaoQuartoById(id);
        if (!avaliacaoQuarto.isPresent()) {
            return new ResponseEntity("AvaliacaoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacaoQuarto.map(AvaliacaoQuartoDTO::create));
    }

    public AvaliacaoQuarto converter(AvaliacaoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoQuarto avaliacaoQuarto = modelMapper.map(dto, AvaliacaoQuarto.class);
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                avaliacaoQuarto.setTipoQuarto(null);
            } else {
                avaliacaoQuarto.setTipoQuarto(tipoQuarto.get());
            }
        }
        return avaliacaoQuarto;
    }
}