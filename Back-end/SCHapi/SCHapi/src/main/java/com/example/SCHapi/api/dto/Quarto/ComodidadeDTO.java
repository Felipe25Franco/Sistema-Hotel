package com.example.SCHapi.api.dto.Quarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.Comodidade;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComodidadeDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Long idTipoComodidade;

    public static ComodidadeDTO create(Comodidade comodidade) {
        ModelMapper modelMapper = new ModelMapper();
        ComodidadeDTO dto = modelMapper.map(comodidade, ComodidadeDTO.class);

        //dto.idTipoComodidade = comodidade.getTipoComodidade().getId();
        return dto;
    }
}
