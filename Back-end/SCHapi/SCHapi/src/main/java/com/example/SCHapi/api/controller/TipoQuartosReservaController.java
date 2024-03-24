package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.TipoQuartosReservaDTO;
import com.example.SCHapi.model.entity.TipoQuartosReserva;

public class TipoQuartosReservaController {
    public TipoQuartosReserva converter(TipoQuartosReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoQuartosReserva.class);
    }
}