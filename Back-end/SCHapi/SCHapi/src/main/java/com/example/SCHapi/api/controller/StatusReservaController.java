package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.StatusReservaDTO;
import com.example.SCHapi.model.entity.StatusReserva;

public class StatusReservaController {
    public StatusReserva converter(StatusReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusReserva.class);
    }
}