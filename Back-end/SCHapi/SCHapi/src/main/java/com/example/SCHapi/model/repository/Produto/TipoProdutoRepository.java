package com.example.SCHapi.model.repository.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Produto.TipoProduto;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {
}
