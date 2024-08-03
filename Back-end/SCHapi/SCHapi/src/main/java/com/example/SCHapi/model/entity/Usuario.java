package com.example.SCHapi.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




import java.util.Date;

import com.example.SCHapi.model.entity.Pessoa.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String senha;
    private String telefone1;
    private String telefone2;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    // @ManyToOne
    // private Uf uf;
    // @ManyToOne
    // private Pais pais;

}
