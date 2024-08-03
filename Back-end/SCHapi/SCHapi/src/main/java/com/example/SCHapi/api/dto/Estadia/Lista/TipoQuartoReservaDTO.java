package com.example.SCHapi.api.dto.Estadia.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoQuartoReservaDTO {

    private Long id;
    private Long idReserva;
    private Long idTipoQuarto;

    public static TipoQuartoReservaDTO create(TipoQuartoReserva tipoQuartoReserva) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoReservaDTO dto = modelMapper.map(tipoQuartoReserva, TipoQuartoReservaDTO.class);

        return dto;
    }
}
