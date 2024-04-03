package com.example.SCHapi.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
