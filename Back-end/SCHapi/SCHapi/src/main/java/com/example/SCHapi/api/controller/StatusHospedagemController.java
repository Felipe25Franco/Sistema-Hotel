package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.StatusHospedagemDTO;
import com.example.SCHapi.model.entity.StatusHospedagem;

public class StatusHospedagemController {
    public StatusHospedagem converter(StatusHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusHospedagem.class);
    }
}