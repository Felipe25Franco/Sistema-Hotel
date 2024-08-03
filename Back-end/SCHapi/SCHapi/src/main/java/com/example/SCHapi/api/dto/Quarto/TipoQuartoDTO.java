package com.example.SCHapi.api.dto.Quarto;

import com.example.SCHapi.api.dto.Quarto.Lista.ComodidadeTipoQuartoDTOList;
import com.example.SCHapi.api.dto.Quarto.Lista.TipoCamaTipoQuartoDTOList;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.hibernate.mapping.List;
import org.modelmapper.ModelMapper;

import java.util.List;

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
    private List<TipoCamaTipoQuartoDTOList> camaTipoQuarto;
    private List<ComodidadeTipoQuartoDTOList> comodidadeTipoQuarto;
    // private Long idTipoCama;
    // private int qntTipoCama;
    // private Long idComodidade;
    //TEM Q FAZER AS LISTAS


    // public static TipoQuartoDTO create(TipoQuarto tipoQuarto, List<ComodidadeTipoQuarto> comodidadeTipoQuarto, List<TipoCamaTipoQuarto> camaTipoQuarto) {

    public static TipoQuartoDTO create(TipoQuarto tipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoDTO dto = modelMapper.map(tipoQuarto, TipoQuartoDTO.class);

        dto.limiteAdulto = tipoQuarto.getLimiteAdultos();
        dto.limiteCrianca = tipoQuarto.getLimiteCriancas();
        
        dto.comodidadeTipoQuarto = ComodidadeTipoQuartoDTOList.createList(tipoQuarto.getComodidadeTipoQuarto());
        dto.camaTipoQuarto = TipoCamaTipoQuartoDTOList.createList(tipoQuarto.getTipoCamaTipoQuarto());
        // dto.idTipoCama = tipoQuarto.getTipoCama().getId();
        // dto.qntTipoCama = tipoQuarto.getTipoCama().getOcupantes();
        // dto.idComodidade = tipoQuarto.getComodidade().getId();


        return dto;
    }
}
