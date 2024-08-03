package com.example.SCHapi.model.entity.Estadia;

import com.example.SCHapi.model.entity.Quarto.TipoQuarto;

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
    private Float nota;
    private String comentario;

    @ManyToOne
    private Hospedagem hospedagem;
    @ManyToOne
    private TipoQuarto tipoQuarto;

}
