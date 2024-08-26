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

    public static List<TipoQuartoReservaDTOList> createList(List<TipoQuartoReserva> list) {
        List<TipoQuartoReservaDTOList> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (TipoQuartoReserva tipoQuartoReserva : list) {
            TipoQuartoReservaDTOList dto = TipoQuartoReservaDTOList.create(tipoQuartoReserva);
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
