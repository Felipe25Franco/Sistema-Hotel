package com.example.SCHapi.model.entity.Quarto.Lista;

import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ComodidadeTipoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;

    @ManyToOne
    private TipoQuarto tipoQuarto;
    @ManyToOne
    private Comodidade comodidade;
    

}
