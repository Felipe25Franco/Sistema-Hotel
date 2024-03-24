package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidadeVagas;
    private float valorTotal;


    @ManyToOne
    private ServicoController servico;
    @ManyToOne
    private Hospedagem hospedagem;
}
