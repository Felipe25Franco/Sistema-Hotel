package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.entity.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusReservaDTO {

    private Long id;
    private String titulo;

    public static StatusReservaDTO create(StatusReserva statusReserva) {
        ModelMapper modelMapper = new ModelMapper();
        StatusReservaDTO dto = modelMapper.map(statusReserva, StatusReservaDTO.class);

        return dto;
    }
}
