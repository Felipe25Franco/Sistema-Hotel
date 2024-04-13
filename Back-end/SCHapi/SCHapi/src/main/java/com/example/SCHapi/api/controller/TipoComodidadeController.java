package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.TipoProdutoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.TipoProduto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.TipoComodidadeDTO;
import com.example.SCHapi.model.entity.TipoComodidade;
import com.example.SCHapi.service.TipoComodidadeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoComodidades")
@RequiredArgsConstructor
public class TipoComodidadeController {
    private final TipoComodidadeService service;

    @GetMapping()
    public ResponseEntity get() {
       List<TipoComodidade> tipoComodidades = service.getTipoComodidades();
        return ResponseEntity.ok(tipoComodidades.stream().map(TipoComodidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoComodidade> tipoComodidade = service.getTipoComodidadeById(id);
        if (!tipoComodidade.isPresent()) {
            return new ResponseEntity("TipoComodidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoComodidade.map(TipoComodidadeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoComodidadeDTO dto) {
        try {
            TipoComodidade tipoComodidade = converter(dto);
            tipoComodidade = service.salvar(tipoComodidade);
            return new ResponseEntity(tipoComodidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoComodidade converter(TipoComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoComodidade.class);
    }
}