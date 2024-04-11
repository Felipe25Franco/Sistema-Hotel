package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.TipoProdutoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.TipoProduto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.TipoServicoDTO;
import com.example.SCHapi.model.entity.TipoServico;
import com.example.SCHapi.service.TipoServicoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoServicos")
@RequiredArgsConstructor
public class TipoServicoController {

    private final TipoServicoService service;

    @GetMapping()
    public ResponseEntity get() {
       List<TipoServico> tipoServicos = service.getTipoServicos();
        return ResponseEntity.ok(tipoServicos.stream().map(TipoServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoServico> tipoServico = service.getTipoServicoById(id);
        if (!tipoServico.isPresent()) {
            return new ResponseEntity("TipoServico não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoServico.map(TipoServicoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoServicoDTO dto) {
        try {
            TipoServico tipoServico = converter(dto);
            tipoServico = service.salvar(tipoServico);
            return new ResponseEntity(tipoServico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoServico converter(TipoServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoServico.class);
    }
}