package com.example.SCHapi.model.repository.Produto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Produto.TipoProduto;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByTipoProduto(Optional<TipoProduto> TipoProduto);
}
