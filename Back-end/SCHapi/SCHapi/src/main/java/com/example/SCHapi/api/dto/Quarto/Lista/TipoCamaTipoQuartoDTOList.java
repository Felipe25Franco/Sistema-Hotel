package com.example.SCHapi.api.dto.Quarto.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCamaTipoQuartoDTOList {
    private Long id;
    private Long idRow;
    private Integer quantidade;
    private Long idTipoQuarto;
    private Long idTipoCama;

    public static TipoCamaTipoQuartoDTOList create(TipoCamaTipoQuarto tipoCamaTipoQuarto) {
        TipoCamaTipoQuartoDTOList dto = new TipoCamaTipoQuartoDTOList();
        dto.setId(tipoCamaTipoQuarto.getId());
        dto.setIdTipoQuarto(tipoCamaTipoQuarto.getTipoQuarto().getId());
        dto.setIdTipoCama(tipoCamaTipoQuarto.getTipoCama().getId());
        dto.setQuantidade(tipoCamaTipoQuarto.getQuantidade());
        return dto;
    }

    public static List<TipoCamaTipoQuartoDTOList> createList(List<TipoCamaTipoQuarto> list) {
        List<TipoCamaTipoQuartoDTOList> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (TipoCamaTipoQuarto tipoCamaTipoQuarto : list) {
            TipoCamaTipoQuartoDTOList dto = TipoCamaTipoQuartoDTOList.create(tipoCamaTipoQuarto);
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
