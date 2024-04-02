package com.example.SCHapi.api.controller;



import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.AdministradorGeralDTO;
import com.example.SCHapi.model.entity.AdministradorGeral;

public class AdministradorGeralController {
    public AdministradorGeral converter(AdministradorGeralDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AdministradorGeral.class);
    }
}