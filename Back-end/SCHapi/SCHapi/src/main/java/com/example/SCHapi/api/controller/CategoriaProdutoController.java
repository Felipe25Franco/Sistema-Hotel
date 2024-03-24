package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.CategoriaProdutoDTO;
import com.example.SCHapi.model.entity.CategoriaProduto;

public class CategoriaProdutoController {
    public CategoriaProduto converter(CategoriaProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CategoriaProduto.class);
    }
}