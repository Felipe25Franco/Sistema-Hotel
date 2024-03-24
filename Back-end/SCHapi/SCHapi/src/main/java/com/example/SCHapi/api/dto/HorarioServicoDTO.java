package com.example.SCHapi.api.dto;
import java.util.Date;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.HorarioServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioServicoDTO {
    private Long id;
    private String status;
    private int vagasTotal;
    private int vagasOcupadas;
    private Date data;
    private String horarioInicio;
    private String horarioFim;

    public static HorarioServicoDTO create(HorarioServico horarioServico) {
        ModelMapper modelMapper = new ModelMapper();
        HorarioServicoDTO dto = modelMapper.map(horarioServico, HorarioServicoDTO.class);

        return dto;
    }
}
