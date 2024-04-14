package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.TipoCamaDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.TipoCama;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.ComodidadeDTO;
import com.example.SCHapi.model.entity.TipoComodidade;
import com.example.SCHapi.model.entity.Comodidade;
import com.example.SCHapi.service.TipoComodidadeService;
import com.example.SCHapi.service.ComodidadeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comodidades")
@RequiredArgsConstructor
public class ComodidadeController {

    private final ComodidadeService service;
    private final TipoComodidadeService tipoComodidadeService;
    
    @GetMapping()
    public ResponseEntity get() {
       List<Comodidade> comodidades = service.getComodidades();
        return ResponseEntity.ok(comodidades.stream().map(ComodidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Comodidade> comodidade = service.getComodidadeById(id);
        if (!comodidade.isPresent()) {
            return new ResponseEntity("Comodidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comodidade.map(ComodidadeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ComodidadeDTO dto) {
        try {
            Comodidade comodidade = converter(dto);
            comodidade = service.salvar(comodidade);
            return new ResponseEntity(comodidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Comodidade converter(ComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Comodidade comodidade = modelMapper.map(dto, Comodidade.class);
        if (dto.getIdTipoComodidade() != null) {
            Optional<TipoComodidade> tipoComodidade = tipoComodidadeService.getTipoComodidadeById(dto.getIdTipoComodidade());
            if (!tipoComodidade.isPresent()) {
                comodidade.setTipoComodidade(null);
            } else {
                comodidade.setTipoComodidade(tipoComodidade.get());
            }
        }
        return comodidade;
    }
}