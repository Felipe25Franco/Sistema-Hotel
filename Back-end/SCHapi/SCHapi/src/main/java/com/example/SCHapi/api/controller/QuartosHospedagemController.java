package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.QuartosHospedagemDTO;
import com.example.SCHapi.model.entity.QuartosHospedagem;

public class QuartosHospedagemController {
    public QuartosHospedagem converter(QuartosHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, QuartosHospedagem.class);
    }
}