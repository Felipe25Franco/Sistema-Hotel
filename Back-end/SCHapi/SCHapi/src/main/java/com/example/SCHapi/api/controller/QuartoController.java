package com.example.SCHapi.api.controller;

import com.example.SCHapi.service.HotelService;
import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.QuartoDTO;
import com.example.SCHapi.model.entity.Quarto;

public class QuartoController {
    private final HotelService hotelService;
    public Quarto converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);

        if (dto.get)

        return quarto;
    }
}