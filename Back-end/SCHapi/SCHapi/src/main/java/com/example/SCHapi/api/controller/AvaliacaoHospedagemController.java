package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.AvaliacaoHospedagemDTO;
import com.example.SCHapi.model.entity.AvaliacaoHospedagem;

import java.util.Optional;

public class AvaliacaoHospedagemController {
    public AvaliacaoHospedagem converter(AvaliacaoHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AvaliacaoHospedagem.class);


    }
}