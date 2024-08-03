package com.example.SCHapi.model.entity.Estadia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Float valorReserva;
    private String dataInicio;
    private String dataFim;

    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private StatusReserva statusReserva;

    @JsonIgnore
    @OneToMany (mappedBy = "reserva")
    private List<TipoQuartoReserva> tipoQuartoReserva;


}
