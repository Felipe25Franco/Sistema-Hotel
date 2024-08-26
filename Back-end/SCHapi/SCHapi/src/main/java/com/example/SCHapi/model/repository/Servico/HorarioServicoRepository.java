package com.example.SCHapi.model.repository.Servico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;

public interface HorarioServicoRepository extends JpaRepository<HorarioServico, Long> {
    
    List<HorarioServico> findByServico(Optional<Servico> servcio);
}
