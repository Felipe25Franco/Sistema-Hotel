package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.AvaliacaoQuartoDTO;
import com.example.SCHapi.model.entity.AvaliacaoQuarto;

public class AvaliacaoQuartoController {
    public AvaliacaoQuarto converter(AvaliacaoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AvaliacaoQuarto.class);
    }
}