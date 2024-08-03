package com.example.SCHapi.model.entity.Quarto;

import com.example.SCHapi.model.entity.Pessoa.Hotel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer andar;
    private String bloco;

    @ManyToOne
    private TipoQuarto tipoQuarto;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private StatusQuarto statusQuarto;
}
