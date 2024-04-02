package com.example.SCHapi.model.repository;

import com.example.SCHapi.model.entity.Financeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {
}
