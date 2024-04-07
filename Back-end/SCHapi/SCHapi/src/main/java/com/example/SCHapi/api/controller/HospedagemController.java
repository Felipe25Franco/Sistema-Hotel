package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.HospedagemDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hospedagens")
@RequiredArgsConstructor
public class HospedagemController {
    private final HospedagemService service;
    private final ClienteService clienteService;
    private final HotelService hotelService;
    private final FuncionarioService funcionarioService;
    private final StatusHospedagemService statushospedagemService;
    private final AvaliacaoHospedagemService avaliacaohospedagemService;
    private final ReservaService reservaService;

    @GetMapping()
    public ResponseEntity get() {
       List<Hospedagem> hospedagens = service.getHospedagens();
        return ResponseEntity.ok(hospedagens.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospedagem.map(HospedagemDTO::create));
    }

    public Hospedagem converter(HospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hospedagem hospedagem = modelMapper.map(dto, Hospedagem.class);
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        StatusHospedagem statusHospedagem = modelMapper.map(dto, StatusHospedagem.class);
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        AvaliacaoHospedagem avaliacaoHospedagem = modelMapper.map(dto, AvaliacaoHospedagem.class);

        return hospedagem;
    }
}