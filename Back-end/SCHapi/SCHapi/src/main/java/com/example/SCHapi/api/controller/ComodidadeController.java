package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ComodidadeDTO;
import com.example.SCHapi.model.entity.Comodidade;

public class ComodidadeController {
    public Comodidade converter(ComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Comodidade.class);
    }
}