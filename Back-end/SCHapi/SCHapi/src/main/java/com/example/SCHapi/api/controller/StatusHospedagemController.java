package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.StatusHospedagemDTO;

import com.example.SCHapi.model.entity.StatusHospedagem;

import com.example.SCHapi.service.StatusHospedagemService;
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
@RequestMapping("/api/v1/statusHospedagens")
@RequiredArgsConstructor
public class StatusHospedagemController {

    private final StatusHospedagemService service;

    @GetMapping()
    public ResponseEntity get() {
        List<StatusHospedagem> statusHospedagens = service.getStatusHospedagem();
        return ResponseEntity.ok(statusHospedagens.stream().map(StatusHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusHospedagem> statusHospedagem = service.getStatusHospedagemById(id);
        if (!statusHospedagem.isPresent()) {
            return new ResponseEntity("statusHospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusHospedagem.map(StatusHospedagemDTO::create));
    }

    public StatusHospedagem converter(StatusHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusHospedagem.class);
    }

}
