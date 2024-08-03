package com.example.SCHapi.model.repository.Quarto.Lista;

import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCamaTipoQuartoRepository extends JpaRepository<TipoCamaTipoQuarto, Long> {
    List<TipoCamaTipoQuarto> findByTipoQuarto(Optional<TipoQuarto> tipoQuarto);
}
