package com.example.SCHapi.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String senha;
    private String telefone1;
    private String telefone2;


}
