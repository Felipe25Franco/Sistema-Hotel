package com.example.SCHapi.model.repository.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
