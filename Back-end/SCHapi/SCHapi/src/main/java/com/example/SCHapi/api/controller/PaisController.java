package com.example.SCHapi.api.controller;

import com.example.SCHapi.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.PaisDTO;
import com.example.SCHapi.model.entity.Pais;
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
@RequestMapping("/api/v1/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Pais> paises = service.getPaises();
        return ResponseEntity.ok(paises.stream().map(PaisDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pais> pais = service.getPaisById(id);
        if (!pais.isPresent()) {
            return new ResponseEntity("Pais não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pais.map(PaisDTO::create));
    }
    
    public Pais converter(PaisDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Pais.class);
    }
}