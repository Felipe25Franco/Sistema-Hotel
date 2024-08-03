package com.example.SCHapi.model.repository.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Pessoa.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
