package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.QuartoDTO;
import com.example.SCHapi.model.entity.Quarto;

public class QuartoController {
    public Quarto converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Quarto.class);
    }
}