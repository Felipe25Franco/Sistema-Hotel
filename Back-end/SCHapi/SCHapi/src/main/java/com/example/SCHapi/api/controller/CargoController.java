package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.CargoDTO;
import com.example.SCHapi.model.entity.Cargo;

public class CargoController {
    public Cargo converter(CargoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cargo.class);
    }
}