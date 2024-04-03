package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AvaliacaoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private float nota;
    private String comentario;

    @ManyToOne
    private Hospedagem hospedagem;
    @OneToOne(cascade = CascadeType.ALL)
    private TipoQuarto tipoQuarto;

}
