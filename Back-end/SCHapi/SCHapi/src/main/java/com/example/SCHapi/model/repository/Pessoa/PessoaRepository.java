package com.example.SCHapi.model.repository.Pessoa;

import com.example.SCHapi.model.entity.Pessoa.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;



public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}