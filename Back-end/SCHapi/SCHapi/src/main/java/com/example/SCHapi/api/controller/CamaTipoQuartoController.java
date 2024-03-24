package com.example.SCHapi.api.controller;


import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.CamaTipoQuartoDTO;
import com.example.SCHapi.model.entity.CamaTipoQuarto;

public class CamaTipoQuartoController {
    public CamaTipoQuarto converter(CamaTipoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CamaTipoQuarto.class);
    }
}