package com.example.SCHapi.api.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.TipoProdutoDTO;
import com.example.SCHapi.model.entity.TipoProduto;
import com.example.SCHapi.service.TipoProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipoProdutos")
@RequiredArgsConstructor
public class TipoProdutoController {

    private final TipoProdutoService service;

    @GetMapping()
    public ResponseEntity get() {
       List<TipoProduto> tipoProdutos = service.getTipoProdutos();
        return ResponseEntity.ok(tipoProdutos.stream().map(TipoProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoProduto> tipoProduto = service.getTipoProdutoById(id);
        if (!tipoProduto.isPresent()) {
            return new ResponseEntity("TipoProduto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoProduto.map(TipoProdutoDTO::create));
    }

    public TipoProduto converter(TipoProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoProduto.class);
    }
}