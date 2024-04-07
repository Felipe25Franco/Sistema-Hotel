package com.example.SCHapi.api.dto;


import com.example.SCHapi.model.entity.StatusHospedagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusHospedagemDTO {
    private Long id;
    private String titulo;

    public static StatusHospedagemDTO create(StatusHospedagem statusHospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        StatusHospedagemDTO dto = modelMapper.map(statusHospedagem, StatusHospedagemDTO.class);

        return dto;
    }
}
