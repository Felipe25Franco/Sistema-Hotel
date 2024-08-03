package com.example.SCHapi.api.dto.Quarto.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComodidadeTipoQuartoDTO {

    private Long id;
    private Long idComodidade;
    private Long idTipoQuarto;

    public static ComodidadeTipoQuartoDTO create(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        ComodidadeTipoQuartoDTO dto = modelMapper.map(comodidadeTipoQuarto, ComodidadeTipoQuartoDTO.class);

        return dto;
    }
}
