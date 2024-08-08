package com.example.SCHapi.api.controller.Servico;

import com.example.SCHapi.api.dto.Servico.TipoServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.TipoServico;
import com.example.SCHapi.service.Servico.TipoServicoService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoServicos")
@RequiredArgsConstructor
@CrossOrigin
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

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoServicoDTO dto) {
        if (!service.getTipoServicoById(id).isPresent()) {
            return new ResponseEntity("TipoServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoServico tipoServico = converter(dto);
            tipoServico.setId(id);
            System.out.println(dto);
            service.salvar(tipoServico);
            return ResponseEntity.ok(tipoServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoServico> tipoServico = service.getTipoServicoById(id);
        if (!tipoServico.isPresent()) {
            return new ResponseEntity("Tipo de Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoServico converter(TipoServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoServico.class);
    }
}