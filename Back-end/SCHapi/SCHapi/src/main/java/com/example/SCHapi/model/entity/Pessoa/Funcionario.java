package com.example.SCHapi.model.entity.Pessoa;




import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@MappedSuperclass
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends Pessoa {
    
    private Float salario;
    private String horaInicio;
    private String horaFim;


    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Hotel hotel;
}
