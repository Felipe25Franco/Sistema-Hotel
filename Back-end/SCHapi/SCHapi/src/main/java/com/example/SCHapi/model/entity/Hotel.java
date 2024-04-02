package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Float avaliacaoMedia;
    private Integer ddd1;
    private Integer num1;
    private Integer ddi2;
    private Integer ddd2;
    private Integer num2;
    private Integer numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;

    @ManyToOne
    private Uf uf;
    @ManyToOne
    private Pais pais;

}
