package com.example.SCHapi.api.dto.Estadia.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Servico.HorarioServico;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelacaoHorarioServicoDTOList2 {
    // horario servico
    private Long id;
    private int quantidadeVagas;
    private Long idHorarioServico;
    private Long idServicoSolicitado;
    
    private Boolean select;

    private Long idRow;
    private String status;
    private int vagasTotal;
    private int vagasOcupadas;
    private String data;
    private String horaInicio;
    private String horaFim;
    private Long idServico;

    public static RelacaoHorarioServicoDTOList2 create(RelacaoHorarioServico relacaoHorarioServico, HorarioServico horarioServico) {
        ModelMapper modelMapper = new ModelMapper();
        RelacaoHorarioServicoDTOList2 dto = modelMapper.map(horarioServico, RelacaoHorarioServicoDTOList2.class);
        if (relacaoHorarioServico != null) {
            dto.setId(relacaoHorarioServico.getId());
            dto.setQuantidadeVagas(relacaoHorarioServico.getQuantidadeVagas());
            dto.setSelect(true);
            dto.setIdServicoSolicitado(relacaoHorarioServico.getServicoSolicitado().getId());
            dto.setIdHorarioServico(horarioServico.getId());
        } else {
            dto.setId(null);
            dto.setQuantidadeVagas(0);
            dto.setSelect(false);
            dto.setIdHorarioServico(horarioServico.getId());
            dto.setIdServicoSolicitado(null);
        }
        return dto;
    }

    public static List<RelacaoHorarioServicoDTOList2> createList(List<RelacaoHorarioServico> relacaoHorarioServicos, List<HorarioServico> horarioServicos) {
        List<RelacaoHorarioServicoDTOList2> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (HorarioServico horarioServico : horarioServicos) {
            RelacaoHorarioServico relacaoHorarioServico = relacaoHorarioServicos.stream()
                    .filter(n -> n.getHorarioServico().equals(horarioServico))
                    .findAny()
                    .orElse(null);

            RelacaoHorarioServicoDTOList2 dto = RelacaoHorarioServicoDTOList2.create(relacaoHorarioServico, horarioServico);
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
