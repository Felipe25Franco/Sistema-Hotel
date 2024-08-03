package com.example.SCHapi.api.dto.Estadia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoQuartoDTO {
    private Long id;
    private Float nota;
    private String comentario;
    private Long idTipoQuarto;
    private Long idHospedagem;


    public static AvaliacaoQuartoDTO create(AvaliacaoQuarto avaliacaoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoQuartoDTO dto = modelMapper.map(avaliacaoQuarto, AvaliacaoQuartoDTO.class);

        //dto.idTipoQuarto = avaliacaoQuarto.getTipoQuarto().getId();
        return dto;
    }
}
