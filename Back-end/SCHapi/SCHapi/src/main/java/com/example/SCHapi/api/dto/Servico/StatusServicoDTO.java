package com.example.SCHapi.api.dto.Servico;

import com.example.SCHapi.model.entity.Servico.StatusServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusServicoDTO {

    private Long id;
    private String titulo;


    public static StatusServicoDTO create(StatusServico statusServico) {
        ModelMapper modelMapper = new ModelMapper();
        StatusServicoDTO dto = modelMapper.map(statusServico, StatusServicoDTO.class);

        return dto;
    }
}
