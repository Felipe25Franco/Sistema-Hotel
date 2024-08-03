package com.example.SCHapi.api.dto.Estadia.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoSolicitadoDTOList {
    private Long id;
    //private Integer quantidadeVagas;
    private float valorTotal;
    private Long idHospedagem;
    private Long idServico;

    public static ServicoSolicitadoDTOList create(ServicoSolicitado servicoSolicitado) {
        ServicoSolicitadoDTOList dto = new ServicoSolicitadoDTOList();
        dto.setId(servicoSolicitado.getId());
        dto.setIdHospedagem(servicoSolicitado.getHospedagem().getId());
        dto.setIdServico(servicoSolicitado.getServico().getId());
        //dto.setQuantidadeVagas(servicoSolicitado.getQuantidadeVagas());
        dto.setValorTotal(servicoSolicitado.getValorTotal());
        return dto;
    }

    public static List<ServicoSolicitadoDTOList> createList (List<ServicoSolicitado> list) {
        List<ServicoSolicitadoDTOList> listDto = new ArrayList<ServicoSolicitadoDTOList>();
        for (ServicoSolicitado servicosolicitado : list) {
            listDto.add(ServicoSolicitadoDTOList.create(servicosolicitado));
        }
        return listDto;
    }
}

