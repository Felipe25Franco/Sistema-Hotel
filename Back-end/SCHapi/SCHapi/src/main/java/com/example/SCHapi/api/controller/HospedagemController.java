package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.HospedagemDTO;
import com.example.SCHapi.model.entity.Hospedagem;

public class HospedagemController {
    public Hospedagem converter(HospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Hospedagem.class);
    }
}