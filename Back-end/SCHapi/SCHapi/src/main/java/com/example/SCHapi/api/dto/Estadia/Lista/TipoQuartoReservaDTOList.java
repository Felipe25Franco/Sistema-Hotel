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
    private Long idRow;
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
        Long idRow = (long) 0; // esse ide é pra padronizar o id da tabla no front pois o id original nao importa
        for (TipoQuartoReserva tipoQuartoReserva : list) {
            listDto.add(TipoQuartoReservaDTOList.create(tipoQuartoReserva));
            idRow++;
            listDto.getLast().setIdRow(idRow);
        }
        return listDto;
    }
}

