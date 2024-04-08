package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.ServicoDTO;
import com.example.SCHapi.model.entity.Servico;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.model.entity.StatusServico;
import com.example.SCHapi.model.entity.TipoServico;
import com.example.SCHapi.service.HotelService;
import com.example.SCHapi.service.ServicoService;
import com.example.SCHapi.service.StatusServicoService;
import com.example.SCHapi.service.TipoServicoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
public class ServicoController {
    private final ServicoService service;
    private final HotelService hotelService;
    private final TipoServicoService tipoServicoService;
    private final StatusServicoService statusServicoService;

    @GetMapping()
    public ResponseEntity get() {
       List<Servico> servicos = service.getServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return new ResponseEntity("Servico não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                servico.setHotel(null);
            } else {
                servico.setHotel(hotel.get());
            }
        }
        if (dto.getIdTipoServico() != null) {
            Optional<TipoServico> tiposervico = tipoServicoService.getTipoServicoById(dto.getIdTipoServico());
            if (!tiposervico.isPresent()) {
                servico.setTipoServico(null);
            } else {
                servico.setTipoServico(tiposervico.get());
            }
        }
        if (dto.getIdStatusServico() != null) {
            Optional<StatusServico> statusservico = statusServicoService.getStatusServicoById(dto.getIdStatusServico());
            if (!statusservico.isPresent()) {
                servico.setStatusServico(null);
            } else {
                servico.setStatusServico(statusservico.get());
            }
        }
        return servico;
    }
}