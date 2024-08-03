package com.example.SCHapi.model.repository.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ServicoSolicitadoRepository extends JpaRepository<ServicoSolicitado, Long> {
    List<ServicoSolicitado> findByHospedagem(Optional<Hospedagem> hospedagem);

}