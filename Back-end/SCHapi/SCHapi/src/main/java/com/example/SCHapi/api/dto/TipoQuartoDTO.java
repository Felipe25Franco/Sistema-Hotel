package com.example.SCHapi.api.dto;

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
    private Integer limiteAdultos;
    private Integer limiteCriancas;
    private Float precoBase;
    private Float avaliacaoMedia;
    private Integer diasCancelarReserva;
    private Float area;
    private Long idTipoCama;
    private int ocupantes;
    private Long idComodidade;



    public static TipoQuartoDTO create(TipoQuarto tipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoDTO dto = modelMapper.map(tipoQuarto, TipoQuartoDTO.class);

        //dto.idTipoCama = tipoQuarto.getTipoCama().getId();
        //dto.ocupantes = tipoQuarto.getTipoCama().getOcupantes();
        // dto.idComodidade = tipoQuarto.getComodidade().getId();


        return dto;
    }
}
