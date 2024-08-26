package com.example.SCHapi.model.repository.Estadia.Lista;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;



public interface RelacaoHorarioServicoRepository extends JpaRepository<RelacaoHorarioServico, Long> {
    List<RelacaoHorarioServico> findByServicoSolicitado(Optional<ServicoSolicitado> servicosolicitado);
}