package com.example.SCHapi.model.entity.Pessoa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String cargo;
    private String descricao;
    private Float salarioBase;

    @ManyToOne
    private Hotel hotel;

}
