package com.example.SCHapi.api.dto.Quarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.TipoComodidade;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoComodidadeDTO {
    private Long id;
    private String titulo;
    private String descricao;

    public static TipoComodidadeDTO create(TipoComodidade tipoComodidade) {
        ModelMapper modelMapper = new ModelMapper();
        TipoComodidadeDTO dto = modelMapper.map(tipoComodidade, TipoComodidadeDTO.class);

        return dto;
    }
}
