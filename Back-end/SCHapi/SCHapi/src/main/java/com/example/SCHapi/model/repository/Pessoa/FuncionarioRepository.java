package com.example.SCHapi.model.repository.Pessoa;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Pessoa.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
