package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;


    @ManyToOne
    private Uf uf;
   //@OneToOne(cascade = CascadeType.ALL)
    //private Hotel hotel;
    // @OneToOne(cascade = CascadeType.ALL)
    // private Usuario usuario;
}
