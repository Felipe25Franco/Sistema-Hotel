package com.example.SCHapi.api.dto.Estadia.Lista;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelacaoHorarioServicoDTOList {
    private Long id;
    private int quantidadeVagas;
    private Long idHorarioServico;
    private Long idServicoSolicitado;

    public static RelacaoHorarioServicoDTOList create(RelacaoHorarioServico relacaoHorarioServico) {
        RelacaoHorarioServicoDTOList dto = new RelacaoHorarioServicoDTOList();
        dto.setId(relacaoHorarioServico.getId());
        dto.setIdHorarioServico(relacaoHorarioServico.getHorarioServico().getId());
        dto.setIdServicoSolicitado(relacaoHorarioServico.getServicoSolicitado().getId());
        dto.setQuantidadeVagas(relacaoHorarioServico.getQuantidadeVagas());
        return dto;
    }

    public static List<RelacaoHorarioServicoDTOList> createList (List<RelacaoHorarioServico> list) {
        List<RelacaoHorarioServicoDTOList> listDto = new ArrayList<RelacaoHorarioServicoDTOList>();
        for (RelacaoHorarioServico relacaoHorarioServico : list) {
            listDto.add(RelacaoHorarioServicoDTOList.create(relacaoHorarioServico));
        }
        return listDto;
    }
}
