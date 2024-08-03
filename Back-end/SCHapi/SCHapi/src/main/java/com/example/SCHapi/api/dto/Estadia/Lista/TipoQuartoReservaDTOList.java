package com.example.SCHapi.api.dto.Estadia.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoQuartoReservaDTOList {
    private Long id;
    private Long tipoQuarto;
    private Integer qtd;

    public static TipoQuartoReservaDTOList create(TipoQuartoReserva tipoQuartoReserva) {
        TipoQuartoReservaDTOList dto = new TipoQuartoReservaDTOList();
        dto.setId(tipoQuartoReserva.getId());
        dto.setTipoQuarto(tipoQuartoReserva.getTipoQuarto().getId());
        dto.setQtd(tipoQuartoReserva.getQtd());
        return dto;
    }

    public static List<TipoQuartoReservaDTOList> createList (List<TipoQuartoReserva> list) {
        List<TipoQuartoReservaDTOList> listDto = new ArrayList<TipoQuartoReservaDTOList>();
        for (TipoQuartoReserva tipoQuartoReserva : list) {
            listDto.add(TipoQuartoReservaDTOList.create(tipoQuartoReserva));
        }
        return listDto;
    }
}

