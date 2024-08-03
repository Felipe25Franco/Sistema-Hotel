package com.example.SCHapi.api.dto.Quarto;

import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusQuartoDTO {
    private Long id;
    private String titulo;

    public static StatusQuartoDTO create(StatusQuarto statusQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        StatusQuartoDTO dto = modelMapper.map(statusQuarto, StatusQuartoDTO.class);

        return dto;
    }
}
