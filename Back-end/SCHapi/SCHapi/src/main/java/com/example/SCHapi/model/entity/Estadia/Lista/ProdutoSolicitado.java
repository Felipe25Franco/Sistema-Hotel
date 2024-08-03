package com.example.SCHapi.model.entity.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Produto.Produto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;
    private float valorTotal;


    @ManyToOne
    private Hospedagem hospedagem;
    @ManyToOne
    private Produto produto;
}
