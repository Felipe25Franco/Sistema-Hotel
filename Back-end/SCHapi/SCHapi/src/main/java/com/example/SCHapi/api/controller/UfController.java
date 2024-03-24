package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.UfDTO;
import com.example.SCHapi.model.entity.Uf;

public class UfController {
    public Uf converter(UfDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Uf.class);
    }
}