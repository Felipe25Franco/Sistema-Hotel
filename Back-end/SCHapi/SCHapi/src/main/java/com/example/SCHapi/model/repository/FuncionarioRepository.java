package com.example.SCHapi.model.repository;

import com.example.SCHapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<FuncionarioController, Long> {
}
