package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ReservaDTO;
import com.example.SCHapi.model.entity.Reserva;

public class ReservaController {
    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Reserva.class);
    }
}