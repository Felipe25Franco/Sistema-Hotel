package com.example.SCHapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusReserva;
    private float valorReserva;
    private Date dataInicio;
    private Date dataFim;

    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private FuncionarioController funcionario;
    @ManyToOne
    private Hotel hotel;


}
