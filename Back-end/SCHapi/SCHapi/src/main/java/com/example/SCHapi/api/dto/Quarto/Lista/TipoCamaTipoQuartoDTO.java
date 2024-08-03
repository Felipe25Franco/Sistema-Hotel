package com.example.SCHapi.api.dto.Quarto.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoCamaTipoQuartoDTO {

    private Long id;
    private Long idTipoCama;
    private Long idTipoQuarto;

    public static TipoCamaTipoQuartoDTO create(TipoCamaTipoQuarto tipoCamaTipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoCamaTipoQuartoDTO dto = modelMapper.map(tipoCamaTipoQuarto, TipoCamaTipoQuartoDTO.class);

        return dto;
    }
}
