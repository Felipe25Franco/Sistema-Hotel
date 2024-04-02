package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.EnderecoDTO;
import com.example.SCHapi.model.entity.Endereco;

public class EnderecoController {
    public Endereco converter(EnderecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Endereco.class);
    }
}