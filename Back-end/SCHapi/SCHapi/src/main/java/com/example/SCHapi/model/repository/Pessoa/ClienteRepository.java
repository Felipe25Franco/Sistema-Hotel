package com.example.SCHapi.model.repository.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Pessoa.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
