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
    private String telefone1;
    private String telefone2;



    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
}
