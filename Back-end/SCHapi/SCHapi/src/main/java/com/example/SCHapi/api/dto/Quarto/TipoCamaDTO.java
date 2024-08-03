package com.example.SCHapi.api.dto.Quarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.TipoCama;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCamaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Integer ocupantes;

    public static TipoCamaDTO create(TipoCama tipoCama) {
        ModelMapper modelMapper = new ModelMapper();
        TipoCamaDTO dto = modelMapper.map(tipoCama, TipoCamaDTO.class);

        return dto;
    }
}
