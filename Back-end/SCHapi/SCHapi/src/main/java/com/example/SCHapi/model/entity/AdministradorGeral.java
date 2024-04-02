package com.example.SCHapi.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor

public class AdministradorGeral extends Funcionario{

    private String descricao;
}
