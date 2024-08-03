package com.example.SCHapi.api.dto.Servico.Lista;


import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelacaoHorarioServicoDTO {

    private Long id;
    private int quantidadeVagas;
    private Long idHorarioServico;
    private Long idServicoSolicitado;

    public static RelacaoHorarioServicoDTO create(RelacaoHorarioServico relacaoHorarioServico) {
        ModelMapper modelMapper = new ModelMapper();
        RelacaoHorarioServicoDTO dto = modelMapper.map(relacaoHorarioServico, RelacaoHorarioServicoDTO.class);

        return dto;
    }
}
