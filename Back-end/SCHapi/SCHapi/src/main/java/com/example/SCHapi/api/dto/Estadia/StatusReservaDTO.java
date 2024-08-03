package com.example.SCHapi.api.dto.Estadia;

import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusReservaDTO {

    private Long id;

    private String titulo;

    public static StatusReservaDTO create(StatusReserva statusReserva) {
        ModelMapper modelMapper = new ModelMapper();
        StatusReservaDTO dto = modelMapper.map(statusReserva, StatusReservaDTO.class);


        return dto;
    }
}
