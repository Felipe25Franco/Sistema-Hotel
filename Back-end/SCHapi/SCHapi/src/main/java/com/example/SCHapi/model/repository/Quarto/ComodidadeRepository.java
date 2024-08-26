package com.example.SCHapi.model.repository.Quarto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

public interface ComodidadeRepository extends JpaRepository<Comodidade, Long> {
    List<Comodidade> findByTipoComodidade(Optional<TipoComodidade> TipoComodidade);
}
