package com.example.SCHapi.api.dto.Estadia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvaliacaoHospedagemDTO {

    private Long id;
    private Float nota;
    private String comentario;
    //private Long idHospedagem;
    //private Long hotel_id;

    public static AvaliacaoHospedagemDTO create(AvaliacaoHospedagem avaliacaoHospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoHospedagemDTO dto = modelMapper.map(avaliacaoHospedagem, AvaliacaoHospedagemDTO.class);

        //dto.idHospedagem = avaliacaoHospedagem.
        return dto;
    }
}

