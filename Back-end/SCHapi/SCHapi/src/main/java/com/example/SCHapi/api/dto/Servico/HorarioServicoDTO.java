package com.example.SCHapi.api.dto.Servico;

import com.example.SCHapi.model.entity.Servico.HorarioServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioServicoDTO {

    private Long id;
    private Long idRow;
    private String status;
    private int vagasTotal;
    private int vagasOcupadas;
    private String data;
    private String horaInicio;
    private String horaFim;
    private Long idServico;

    public static HorarioServicoDTO create(HorarioServico horarioServico) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(horarioServico, HorarioServicoDTO.class);
    }

    public static List<HorarioServicoDTO> createList(List<HorarioServico> list) {
        List<HorarioServicoDTO> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (HorarioServico horarioServico : list) {
            HorarioServicoDTO dto = HorarioServicoDTO.create(horarioServico);
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
