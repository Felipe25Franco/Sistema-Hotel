package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.CamaTipoQuarto;
import com.example.SCHapi.model.entity.ComodidadeTipoQuarto;

import com.example.SCHapi.model.entity.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoQuartoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Integer limiteAdulto;
    private Integer limiteCrianca;
    private Float precoBase;
    private Float avaliacaoMedia;
    private Integer diasCancelarReserva;
    private Float area;
    private CamaTipoQuarto CamaTipoQuarto;
    private ComodidadeTipoQuarto ComodidadeTipoQuarto;

    public static TipoQuartoDTO create(TipoQuartoController tipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoDTO dto = modelMapper.map(tipoQuarto, TipoQuartoDTO.class);

        return dto;
    }
}
