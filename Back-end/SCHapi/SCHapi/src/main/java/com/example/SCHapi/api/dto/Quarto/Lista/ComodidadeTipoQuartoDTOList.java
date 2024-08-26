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
    private Long idRow;
    private Integer qtd;
    private Long idComodidade;

    public static ComodidadeTipoQuartoDTOList create(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        ComodidadeTipoQuartoDTOList dto = new ComodidadeTipoQuartoDTOList();
        dto.setId(comodidadeTipoQuarto.getId());
        dto.setIdComodidade(comodidadeTipoQuarto.getComodidade().getId());
        dto.setQtd(comodidadeTipoQuarto.getQuantidade());
        return dto;  
    }

    public static List<ComodidadeTipoQuartoDTOList> createList(List<ComodidadeTipoQuarto> list) {
        List<ComodidadeTipoQuartoDTOList> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (ComodidadeTipoQuarto comodidadeTipoQuarto : list) {
            ComodidadeTipoQuartoDTOList dto = ComodidadeTipoQuartoDTOList.create(comodidadeTipoQuarto);
            listDto.add(dto);
            idRow++;
            // Atualiza o ID da linha no último elemento da lista
            if (!listDto.isEmpty()) {
                listDto.get(listDto.size() - 1).setIdRow(idRow);
            }
        }

        return listDto;
    }
}
