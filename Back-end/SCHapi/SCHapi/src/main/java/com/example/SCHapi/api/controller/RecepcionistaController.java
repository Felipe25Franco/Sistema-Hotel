package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.RecepcionistaDTO;
import com.example.SCHapi.model.entity.Recepcionista;

public class RecepcionistaController {
    public Recepcionista converter(RecepcionistaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Recepcionista.class);
    }
}