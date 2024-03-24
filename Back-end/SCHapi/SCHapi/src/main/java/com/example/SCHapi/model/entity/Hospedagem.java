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
public class Hospedagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusHospedagem;
    private Date dataInicio;
    private Date dataFim1;
    private Date dataFim2;
    private float valorEstadia;
    private float valorConsumo;
    private float valorServicos;
    private float valorEstadiaAdicional;
    private float valorTotalPago;


    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private FuncionarioController funcionario;
    @ManyToOne
    private Hotel hotel;


    @OneToOne
    private Reserva reserva;

}
