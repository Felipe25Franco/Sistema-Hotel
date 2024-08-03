package com.example.SCHapi.model.repository.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoSolicitadoRepository extends JpaRepository<ProdutoSolicitado, Long> {
    List<ProdutoSolicitado> findByHospedagem(Optional<Hospedagem> hospedagem);
}
