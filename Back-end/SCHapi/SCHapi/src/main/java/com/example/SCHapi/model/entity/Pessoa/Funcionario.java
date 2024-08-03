package com.example.SCHapi.model.entity.Pessoa;



import com.example.SCHapi.model.entity.Usuario;

import jakarta.persistence.*;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@MappedSuperclass
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends Usuario{
    
    private Float salario;
    private String horaInicio;
    private String horaFim;


    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Hotel hotel;
}
