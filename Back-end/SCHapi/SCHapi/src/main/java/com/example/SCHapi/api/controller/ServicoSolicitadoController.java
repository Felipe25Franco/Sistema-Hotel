package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ServicoSolicitadoDTO;
import com.example.SCHapi.model.entity.ServicoSolicitado;

public class ServicoSolicitadoController {
    public ServicoSolicitado converter(ServicoSolicitadoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ServicoSolicitado.class);
    }
}