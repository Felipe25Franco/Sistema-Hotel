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

    public static List<TipoCamaTipoQuartoDTOList> createList (List<TipoCamaTipoQuarto> list) {
        List<TipoCamaTipoQuartoDTOList> listDto = new ArrayList<TipoCamaTipoQuartoDTOList>();
        for (TipoCamaTipoQuarto tipoCamaTipoQuarto : list) {
            listDto.add(TipoCamaTipoQuartoDTOList.create(tipoCamaTipoQuarto));
        }
        return listDto;
    }
}

