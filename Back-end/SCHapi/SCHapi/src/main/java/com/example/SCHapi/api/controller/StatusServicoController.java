package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.StatusServicoDTO;
import com.example.SCHapi.model.entity.StatusServico;

public class StatusServicoController {
    public StatusServico converter(StatusServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusServico.class);
    }
}