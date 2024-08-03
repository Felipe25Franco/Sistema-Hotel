package com.example.SCHapi.api.dto.Estadia;

import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusHospedagemDTO {

    private Long id;

    private String titulo;

    public static StatusHospedagemDTO create(StatusHospedagem statusHospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        StatusHospedagemDTO dto = modelMapper.map(statusHospedagem, StatusHospedagemDTO.class);

        return dto;
    }

}
