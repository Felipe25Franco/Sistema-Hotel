package com.example.SCHapi.api.controller.Quarto.Lista;

import com.example.SCHapi.api.dto.Quarto.Lista.ComodidadeTipoQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Quarto.ComodidadeService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;
import com.example.SCHapi.service.Quarto.Lista.ComodidadeTipoQuartoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comodidadeTipoQuartos")
@RequiredArgsConstructor
@CrossOrigin
public class ComodidadeTipoQuartoController {

    private final ComodidadeTipoQuartoService service;
    private final ComodidadeService comodidadeService;
    private final TipoQuartoService tipoQuartoService;

    @GetMapping()
    public ResponseEntity get() {
       List<ComodidadeTipoQuarto> comodidadeTipoQuartos = service.getComodidadeTipoQuartos();
        return ResponseEntity.ok(comodidadeTipoQuartos.stream().map(ComodidadeTipoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ComodidadeTipoQuarto> comodidadeTipoQuarto = service.getComodidadeTipoQuartoById(id);
        if (!comodidadeTipoQuarto.isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comodidadeTipoQuarto.map(ComodidadeTipoQuartoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ComodidadeTipoQuartoDTO dto) {
        try {
            ComodidadeTipoQuarto comodidadeTipoQuarto = converter(dto);
            comodidadeTipoQuarto = service.salvar(comodidadeTipoQuarto);
            return new ResponseEntity(comodidadeTipoQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ComodidadeTipoQuartoDTO dto) {
        if (!service.getComodidadeTipoQuartoById(id).isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ComodidadeTipoQuarto comodidadeTipoQuarto = converter(dto);
            comodidadeTipoQuarto.setId(id);
            System.out.println(dto);
            service.salvar(comodidadeTipoQuarto);
            return ResponseEntity.ok(comodidadeTipoQuarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ComodidadeTipoQuarto> comodidadeTipoQuarto = service.getComodidadeTipoQuartoById(id);
        if (!comodidadeTipoQuarto.isPresent()) {
            return new ResponseEntity("ComodidadeTipoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(comodidadeTipoQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ComodidadeTipoQuarto converter(ComodidadeTipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ComodidadeTipoQuarto comodidadeTipoQuarto = modelMapper.map(dto, ComodidadeTipoQuarto.class);
        if (dto.getIdComodidade() != null) {
            Optional<Comodidade> comodidade = comodidadeService.getComodidadeById(dto.getIdComodidade());
            if (!comodidade.isPresent()) {
                comodidadeTipoQuarto.setComodidade(null);
            } else {
                comodidadeTipoQuarto.setComodidade(comodidade.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                comodidadeTipoQuarto.setTipoQuarto(null);
            } else {
                comodidadeTipoQuarto.setTipoQuarto(tipoQuarto.get());
            }
        }
        return comodidadeTipoQuarto;
    }
}