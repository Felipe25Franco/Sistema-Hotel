package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.HorarioDTO;
import com.example.SCHapi.model.entity.Horario;

public class HorarioController {
    public Horario converter(HorarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Horario.class);
    }
}