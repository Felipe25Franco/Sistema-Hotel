package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Quarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {
    private Long id;
    private Integer numero;
    private Integer andar;
    private String bloco;
    private String status;
    private Long idHotel;
    private Long idTipoQuarto;
    private Long idStatusQuarto;
    public static QuartoDTO create(Quarto quarto) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoDTO dto = modelMapper.map(quarto, QuartoDTO.class);


        return dto;
    }
}
