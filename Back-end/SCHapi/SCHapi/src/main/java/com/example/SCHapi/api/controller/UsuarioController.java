package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.UsuarioDTO;
import com.example.SCHapi.model.entity.Usuario;

public class UsuarioController {
    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }
}