package com.example.SCHapi.model.repository.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Pessoa.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
