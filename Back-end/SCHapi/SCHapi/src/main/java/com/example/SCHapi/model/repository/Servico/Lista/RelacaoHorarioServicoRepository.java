package com.example.SCHapi.model.repository.Servico.Lista;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;



public interface RelacaoHorarioServicoRepository extends JpaRepository<RelacaoHorarioServico, Long> {
    List<RelacaoHorarioServico> findByServicoSolicitado(Optional<ServicoSolicitado> servicosolicitado);
}