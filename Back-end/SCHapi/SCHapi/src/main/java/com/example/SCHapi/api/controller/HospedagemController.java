package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.HospedagemDTO;
import com.example.SCHapi.model.entity.Hospedagem;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.model.entity.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Cliente;
import com.example.SCHapi.model.entity.Funcionario;
import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.entity.TipoQuarto;
import com.example.SCHapi.service.HospedagemService;
import com.example.SCHapi.service.HotelService;
import com.example.SCHapi.service.AvaliacaoHospedagemService;
import com.example.SCHapi.service.ClienteService;
import com.example.SCHapi.service.FuncionarioService;
import com.example.SCHapi.service.StatusHospedagemService;
import com.example.SCHapi.service.TipoQuartoService;

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
        return hospedagem;
    }
}