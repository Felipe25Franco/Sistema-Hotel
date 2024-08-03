package com.example.SCHapi.model.entity.Produto;

import com.example.SCHapi.model.entity.Pessoa.Hotel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Float precoBase;
    private Integer quantidadeEstoque;

    @ManyToOne
    private TipoProduto tipoProduto;
    @ManyToOne
    private Hotel hotel;
}
