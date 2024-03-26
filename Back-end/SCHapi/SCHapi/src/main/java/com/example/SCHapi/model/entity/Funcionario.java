package com.example.SCHapi.model.entity;



import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends Usuario{
    
    private float salario;
    private String horaInicio;
    private String horaFim;


    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Hotel hotel;
}
