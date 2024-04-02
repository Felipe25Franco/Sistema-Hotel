package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.RelacaoHorariosServicoDTO;
import com.example.SCHapi.model.entity.RelacaoHorariosServico;

public class RelacaoHorariosServicoController {
    public RelacaoHorariosServico converter(RelacaoHorariosServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, RelacaoHorariosServico.class);
    }
}