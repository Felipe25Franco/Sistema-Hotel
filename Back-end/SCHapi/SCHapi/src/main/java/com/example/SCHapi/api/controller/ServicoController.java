package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ServicoDTO;
import com.example.SCHapi.model.entity.Servico;

public class ServicoController {
    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Servico.class);
    }
}