package com.example.SCHapi.model.repository;

import com.example.SCHapi.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
