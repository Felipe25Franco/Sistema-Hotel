package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.ComodidadeDTO;
import com.example.SCHapi.api.dto.EnderecoDTO;
import com.example.SCHapi.model.entity.Comodidade;
import com.example.SCHapi.model.entity.Endereco;
import com.example.SCHapi.model.entity.TipoComodidade;
import com.example.SCHapi.model.entity.Uf;
import com.example.SCHapi.service.EnderecoService;
import com.example.SCHapi.service.UfService;
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
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService service;
    private final UfService ufService;

    @GetMapping()
    public ResponseEntity get() {
        List<Endereco> enderecos = service.getEnderecos();
        return ResponseEntity.ok(enderecos.stream().map(EnderecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Endereco> endereco = service.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(endereco.map(EnderecoDTO::create));
    }

    public Endereco converter(EnderecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        Uf uf = modelMapper.map(dto, Uf.class);
        return endereco;
    }
}
