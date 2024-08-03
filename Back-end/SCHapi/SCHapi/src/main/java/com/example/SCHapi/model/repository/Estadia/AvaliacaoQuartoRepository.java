package com.example.SCHapi.model.repository.Estadia;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;

public interface AvaliacaoQuartoRepository extends JpaRepository<AvaliacaoQuarto, Long> {
    List<AvaliacaoQuarto> findByHospedagem(Optional<Hospedagem> hospedagem);
}
