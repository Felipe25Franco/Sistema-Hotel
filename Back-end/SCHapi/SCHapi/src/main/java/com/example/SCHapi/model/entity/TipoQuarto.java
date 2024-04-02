package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
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
    private int limiteCriancas;
    private int limiteAdultos;
    private float precoBase;
    private float avaliacaoMedia;
    private int diasCancelarReserva;
    private float area;

    @OneToOne(cascade = CascadeType.ALL)
    private AvaliacaoQuarto avaliacaoQuarto;

    @ManyToOne
    private TipoCama tipoCama;
    @ManyToOne
    private Comodidade comodidade;
}
