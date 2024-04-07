package com.example.SCHapi.api.controller;

import com.example.SCHapi.api.dto.StatusReservaDTO;
import com.example.SCHapi.api.dto.StatusServicoDTO;
import com.example.SCHapi.model.entity.StatusReserva;
import com.example.SCHapi.model.entity.StatusServico;
import com.example.SCHapi.service.StatusServicoService;
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
@RequestMapping("/api/v1/statusServicos")
@RequiredArgsConstructor
public class StatusServicoController {
    private final StatusServicoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<StatusServico> statusServicos = service.getStatusServico();
        return ResponseEntity.ok(statusServicos.stream().map(StatusServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusServico> statusServico = service.getStatusServicoById(id);
        if (!statusServico.isPresent()) {
            return new ResponseEntity("statusServico não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusServico.map(StatusServicoDTO::create));
    }

    public StatusServico converter(StatusServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusServico.class);
    }
}
