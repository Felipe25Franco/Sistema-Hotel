package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.TipoQuartoDTO;
import com.example.SCHapi.model.entity.TipoQuarto;

public class TipoQuartoController {
    public TipoQuarto converter(TipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoQuarto.class);
    }
}