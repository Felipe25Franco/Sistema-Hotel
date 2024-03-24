package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.GerenteDTO;
import com.example.SCHapi.model.entity.Gerente;

public class GerenteController {
    public Gerente converter(GerenteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Gerente.class);
    }
}