package com.example.SCHapi.api.controller.Estadia;

import com.example.SCHapi.api.dto.Estadia.AvaliacaoHospedagemDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.service.Estadia.AvaliacaoHospedagemService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/avaliacaoHospedagens")
@RequiredArgsConstructor
public class AvaliacaoHospedagemController {

    private final AvaliacaoHospedagemService service;

    @GetMapping()
    public ResponseEntity get() {
        System.out.println(AvaliacaoHospedagem.getMedia((long)1));
        List<AvaliacaoHospedagem> avaliacaoHospedagens = service.getAvaliacaoHospedagens();
        return ResponseEntity.ok(avaliacaoHospedagens.stream().map(AvaliacaoHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AvaliacaoHospedagem> avaliacaoHospedagem = service.getAvaliacaoHospedagemById(id);
        if (!avaliacaoHospedagem.isPresent()) {
            return new ResponseEntity("AvaliacaoHospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacaoHospedagem.map(AvaliacaoHospedagemDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody AvaliacaoHospedagemDTO dto) {
        try {
            AvaliacaoHospedagem avaliacaoHospedagem = converter(dto);
            avaliacaoHospedagem = service.salvar(avaliacaoHospedagem);
            return new ResponseEntity(avaliacaoHospedagem, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AvaliacaoHospedagemDTO dto) {
        if (!service.getAvaliacaoHospedagemById(id).isPresent()) {
            return new ResponseEntity("AvaliacaoHospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AvaliacaoHospedagem avaliacaoHospedagem = converter(dto);
            avaliacaoHospedagem.setId(id);
            System.out.println(dto);
            service.salvar(avaliacaoHospedagem);
            return ResponseEntity.ok(avaliacaoHospedagem);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AvaliacaoHospedagem> avaliacaoHospedagem = service.getAvaliacaoHospedagemById(id);
        if (!avaliacaoHospedagem.isPresent()) {
            return new ResponseEntity("Avaliação de Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(avaliacaoHospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AvaliacaoHospedagem converter(AvaliacaoHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoHospedagem avaliacaohospedagem = modelMapper.map(dto, AvaliacaoHospedagem.class);
        
        return avaliacaohospedagem;
    }
}