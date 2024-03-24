package com.example.SCHapi.model.repository;

import com.example.SCHapi.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoController, Long> {
}
