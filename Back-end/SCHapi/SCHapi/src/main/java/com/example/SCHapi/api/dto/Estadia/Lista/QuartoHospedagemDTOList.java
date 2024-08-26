package com.example.SCHapi.api.dto.Estadia.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoHospedagemDTOList {
    private Long id;
    private Long idRow;
    private Long tipoQuarto;
    private Integer qtd;
    private Long num;

    public static QuartoHospedagemDTOList create(QuartoHospedagem quartoHospedagem) {
        QuartoHospedagemDTOList dto = new QuartoHospedagemDTOList();
        dto.setId(quartoHospedagem.getId());
        dto.setTipoQuarto(quartoHospedagem.getQuarto().getTipoQuarto().getId());
        dto.setQtd(0); // Esse valor é fictício e não existe, cuidado só para não dar erro no DTO do front
        dto.setNum(quartoHospedagem.getQuarto().getId());
        return dto;
    }

    public static List<QuartoHospedagemDTOList> createList(List<QuartoHospedagem> list) {
        List<QuartoHospedagemDTOList> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (QuartoHospedagem quartoHospedagem : list) {
            QuartoHospedagemDTOList dto = QuartoHospedagemDTOList.create(quartoHospedagem);
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
