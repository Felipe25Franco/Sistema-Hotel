package com.example.SCHapi.api.dto.Quarto.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComodidadeTipoQuartoDTOList {
    private Long id;
    private Integer qtd;
    private Long idComodidade;

    public static ComodidadeTipoQuartoDTOList create(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        ComodidadeTipoQuartoDTOList dto = new ComodidadeTipoQuartoDTOList();
        dto.setId(comodidadeTipoQuarto.getId());
        dto.setIdComodidade(comodidadeTipoQuarto.getComodidade().getId());
        dto.setQtd(comodidadeTipoQuarto.getQuantidade());
        return dto;  
    }

    public static List<ComodidadeTipoQuartoDTOList> createList (List<ComodidadeTipoQuarto> list) {
        List<ComodidadeTipoQuartoDTOList> listDto = new ArrayList<ComodidadeTipoQuartoDTOList>();
        for (ComodidadeTipoQuarto comodidadeTipoQuarto : list) {
            listDto.add(ComodidadeTipoQuartoDTOList.create(comodidadeTipoQuarto));
        }
        return listDto;
    }
}

