package com.example.SCHapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.TipoComodidade;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoComodidadeDTO {
    private Long id;
    private String categoria;
    private String descricao;

    public static TipoComodidadeDTO create(TipoComodidade tipoComodidade) {
        ModelMapper modelMapper = new ModelMapper();
        TipoComodidadeDTO dto = modelMapper.map(tipoComodidade, TipoComodidadeDTO.class);
        return dto;
    }
}
