package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.CategoriaComodidadeDTO;
import com.example.SCHapi.model.entity.CategoriaComodidade;

public class CategoriaComodidadeController {
    public CategoriaComodidade converter(CategoriaComodidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CategoriaComodidade.class);
    }
}