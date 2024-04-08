package com.example.SCHapi.api.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.UfDTO;
import com.example.SCHapi.model.entity.Uf;
import com.example.SCHapi.model.entity.Pais;
import com.example.SCHapi.service.PaisService;
import com.example.SCHapi.service.UfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ufs")
@RequiredArgsConstructor
public class UfController {
    
    private final UfService service;
    private final PaisService paisService;

    @GetMapping()
    public ResponseEntity get() {
       List<Uf> ufs = service.getUfs();
        return ResponseEntity.ok(ufs.stream().map(UfDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Uf> uf = service.getUfById(id);
        if (!uf.isPresent()) {
            return new ResponseEntity("Uf não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(uf.map(UfDTO::create));
    }

    public Uf converter(UfDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Uf uf =  modelMapper.map(dto, Uf.class);
        if (dto.getIdPais() != null) {
            Optional<Pais> pais = paisService.getPaisById(dto.getIdPais());
            if (!pais.isPresent()) {
                uf.setPais(null);
            } else {
                uf.setPais(pais.get());
            }
        }
        return uf;
    }
}