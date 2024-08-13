package com.example.SCHapi.model.entity.Quarto;

import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;;

    private String titulo;
    private String descricao;
    private Integer limiteCriancas;
    private Integer limiteAdultos;
    private Float precoBase;
    private Float avaliacaoMedia;
    private Integer diasCancelarReserva;
    private Float area;

    // @OneToOne(cascade = CascadeType.ALL)
    // private AvaliacaoQuarto avaliacaoQuarto;

    @JsonIgnore
    @OneToMany (mappedBy = "tipoQuarto")
    private List<TipoCamaTipoQuarto> tipoCamaTipoQuarto;

    @JsonIgnore
    @OneToMany (mappedBy = "tipoQuarto")
    private List<ComodidadeTipoQuarto> comodidadeTipoQuarto;

}
