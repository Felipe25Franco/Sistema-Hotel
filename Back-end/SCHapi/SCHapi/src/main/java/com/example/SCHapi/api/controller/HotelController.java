package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.HotelDTO;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.model.entity.Pais;
import com.example.SCHapi.model.entity.Endereco;
import com.example.SCHapi.model.entity.Uf;
import com.example.SCHapi.service.HotelService;
import com.example.SCHapi.service.EnderecoService;
import com.example.SCHapi.service.PaisService;
import com.example.SCHapi.service.UfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hoteis")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;
    private final EnderecoService enderecoService;
    private final UfService ufService;
    private final PaisService paisService;

    @GetMapping()
    public ResponseEntity get() {
       List<Hotel> hoteis = service.getHoteis();
        return ResponseEntity.ok(hoteis.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if (!hotel.isPresent()) {
            return new ResponseEntity("Hotel não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

    public Hotel converter(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        hotel.setEndereco(endereco);
        if (dto.getIdUf() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            if (!uf.isPresent()) {
                endereco.setUf(null);
            } else {
                endereco.setUf(uf.get());
            }
        }
        if (dto.getIdPais() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            Optional<Pais> pais = paisService.getPaisById(dto.getIdPais());
            if (!pais.isPresent()) {
                uf.get().setPais(null);
            } else {
                uf.get().setPais(pais.get());
            }
        }
        return hotel;
    }
}