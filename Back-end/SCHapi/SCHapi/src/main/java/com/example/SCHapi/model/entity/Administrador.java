package com.example.SCHapi.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity

@NoArgsConstructor
@AllArgsConstructor

public class Administrador extends Funcionario{

    private String descricao;
}
