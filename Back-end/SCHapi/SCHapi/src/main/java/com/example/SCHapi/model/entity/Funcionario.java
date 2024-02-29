package com.example.SCHapi.model.entity;


import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Funcionario extends Usuario{
    
    private float salario;
    private String horaInicio;
    private String horaFim;


}
