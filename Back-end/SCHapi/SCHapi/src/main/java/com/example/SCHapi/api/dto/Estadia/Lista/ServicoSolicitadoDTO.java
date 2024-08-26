package com.example.SCHapi.api.dto.Estadia.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.api.dto.Servico.HorarioServicoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoSolicitadoDTO {

    private Long id;
    private Long idServico;
    private Long idHospedagem;
    private float valorTotal;

    private List<RelacaoHorarioServicoDTOList2> relacaoHorarioServico;

    public static ServicoSolicitadoDTO create(ServicoSolicitado servicoSolicitado) {
        // ModelMapper modelMapper = new ModelMapper();
        // ServicoSolicitadoDTO dto = modelMapper.map(servicoSolicitado, ServicoSolicitadoDTO.class);
        ServicoSolicitadoDTO dto = new ServicoSolicitadoDTO();
        dto.setId(servicoSolicitado.getId());
        dto.setIdServico(servicoSolicitado.getServico().getId());
        dto.setIdHospedagem(servicoSolicitado.getHospedagem().getId());
        dto.setValorTotal(servicoSolicitado.getValorTotal());
        dto.relacaoHorarioServico = RelacaoHorarioServicoDTOList2.createList(servicoSolicitado.getRelacaoHorarioServico(), servicoSolicitado.getServico().getHorarioServicos());

        return dto;
    }
}
