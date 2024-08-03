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
    private Long tipoQuarto;
    private Integer qtd;
    private Long num;

    public static QuartoHospedagemDTOList create(QuartoHospedagem quartoHospedagem) {
        QuartoHospedagemDTOList dto = new QuartoHospedagemDTOList();
        dto.setId(quartoHospedagem.getId());
        dto.setTipoQuarto(quartoHospedagem.getQuarto().getTipoQuarto().getId());
        dto.setQtd(0); // esse valor é ficticio e nao existe cuidado so pra nao daerro no dto d o front
        dto.setNum(quartoHospedagem.getQuarto().getId());
        return dto;
    }

    public static List<QuartoHospedagemDTOList> createList (List<QuartoHospedagem> list) {
        List<QuartoHospedagemDTOList> listDto = new ArrayList<QuartoHospedagemDTOList>();
        for (QuartoHospedagem quartoHospedagem : list) {
            listDto.add(QuartoHospedagemDTOList.create(quartoHospedagem));
        }
        return listDto;
    }
}

