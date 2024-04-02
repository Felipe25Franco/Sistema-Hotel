package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.RecusosHumanosDTO;
import com.example.SCHapi.model.entity.RecusosHumanos;

public class RecusosHumanosController {
    public RecusosHumanos converter(RecusosHumanosDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, RecusosHumanos.class);
    }
}