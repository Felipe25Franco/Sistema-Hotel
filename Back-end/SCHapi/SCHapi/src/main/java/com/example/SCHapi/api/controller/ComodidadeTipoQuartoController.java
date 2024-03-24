package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ComodidadeTipoQuartoDTO;
import com.example.SCHapi.model.entity.ComodidadeTipoQuarto;

public class ComodidadeTipoQuartoController {
    public ComodidadeTipoQuarto converter(ComodidadeTipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ComodidadeTipoQuarto.class);
    }
}