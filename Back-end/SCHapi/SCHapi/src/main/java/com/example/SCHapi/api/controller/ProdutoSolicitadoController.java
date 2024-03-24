package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.ProdutoSolicitadoDTO;
import com.example.SCHapi.model.entity.ProdutoSolicitado;

public class ProdutoSolicitadoController {
    public ProdutoSolicitado converter(ProdutoSolicitadoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ProdutoSolicitado.class);
    }
}