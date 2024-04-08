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
    private final QuartoHospedagemService quartoHospedagemService;

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

        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                hospedagem.setCliente(null);
            } else {
                hospedagem.setCliente(cliente.get());
            }
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                hospedagem.setHotel(null);
            } else {
                hospedagem.setHotel(hotel.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                hospedagem.setFuncionario(null);
            } else {
                hospedagem.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdStatusHospedagem() != null) {
            Optional<StatusHospedagem> statushospedagem = statushospedagemService.getStatusHospedagemById(dto.getIdStatusHospedagem());
            if (!statushospedagem.isPresent()) {
                hospedagem.setStatusHospedagem(null);
            } else {
                hospedagem.setStatusHospedagem(statushospedagem.get());
            }
        }
        if (dto.getIdAvaliacaoHospedagem() != null) {
            Optional<AvaliacaoHospedagem> avaliacaohospedagem = avaliacaohospedagemService.getAvaliacaoHospedagemById(dto.getIdAvaliacaoHospedagem());
            if (!avaliacaohospedagem.isPresent()) {
                hospedagem.setAvaliacaoHospedagem(null);
            } else {
                hospedagem.setAvaliacaoHospedagem(avaliacaohospedagem.get());
            }
        }
        if (dto.getIdQuartoHospedagem() != null) {
            Optional<QuartoHospedagem> quartohospedagem = quartoHospedagemService.getQuartoHospedagemById(dto.getIdQuartoHospedagem());
            if (!quartohospedagem.isPresent()) {
                hospedagem.setQuartoHospedagem(null);
            } else {
                hospedagem.setQuartoHospedagem(quartohospedagem.get());
            }
        }
        if (dto.getIdReserva() != null) {
            Optional<Reserva> reserva = reservaService.getReservaById(dto.getIdReserva());
            if (!reserva.isPresent()) {
                hospedagem.setReserva(null);
            } else {
                hospedagem.setReserva(reserva.get());
            }
        }

        return hospedagem;
    }
}