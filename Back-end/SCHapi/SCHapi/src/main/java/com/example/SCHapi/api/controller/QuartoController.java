package com.example.SCHapi.api.controller;

import com.example.SCHapi.service.QuartoService;
import com.example.SCHapi.service.StatusQuartoService;
import com.example.SCHapi.service.TipoQuartoService;
import com.example.SCHapi.service.HotelService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.QuartoDTO;
import com.example.SCHapi.model.entity.Quarto;
import com.example.SCHapi.model.entity.StatusQuarto;
import com.example.SCHapi.model.entity.TipoQuarto;
import com.example.SCHapi.model.entity.Hotel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService service;
    private final HotelService hotelService;
    private final TipoQuartoService tipoquartoService;
    private final StatusQuartoService statusquartoService;

    @GetMapping()
    public ResponseEntity get() {
       List<Quarto> quartos = service.getQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if (!quarto.isPresent()) {
            return new ResponseEntity("Quarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    public Quarto converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                quarto.setHotel(null);
            } else {
                quarto.setHotel(hotel.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoquarto = tipoquartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoquarto.isPresent()) {
                quarto.setTipoQuarto(null);
            } else {
                quarto.setTipoQuarto(tipoquarto.get());
            }
        }
        if (dto.getIdStatusQuarto() != null) {
            Optional<StatusQuarto> statusquarto = statusquartoService.getStatusQuartoById(dto.getIdStatusQuarto());
            if (!statusquarto.isPresent()) {
                quarto.setStatusQuarto(null);
            } else {
                quarto.setStatusQuarto(statusquarto.get());
            }
        }
        return quarto;
    }
}