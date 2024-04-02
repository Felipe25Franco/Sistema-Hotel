package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ProdutoDTO;
import com.example.SCHapi.model.entity.Produto;

public class ProdutoController {
    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produto.class);
    }
}