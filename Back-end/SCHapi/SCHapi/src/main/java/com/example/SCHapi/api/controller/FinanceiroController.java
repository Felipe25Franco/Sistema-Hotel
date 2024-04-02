package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.FinanceiroDTO;
import com.example.SCHapi.model.entity.Financeiro;

public class FinanceiroController {
    public Financeiro converter(FinanceiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Financeiro.class);
    }
}