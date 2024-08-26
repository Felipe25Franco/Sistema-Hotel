package com.example.SCHapi.model.entity.Servico;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HorarioServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private int vagasTotal;
    private int vagasOcupadas;
    private String data;
    private String horaInicio;
    private String horaFim;

    @ManyToOne
    private Servico servico;


}
