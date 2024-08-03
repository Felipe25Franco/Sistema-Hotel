package com.example.SCHapi.model.repository.Quarto.Lista;

import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComodidadeTipoQuartoRepository extends JpaRepository<ComodidadeTipoQuarto, Long> {
    List<ComodidadeTipoQuarto> findByTipoQuarto(Optional<TipoQuarto> tipoQuarto);
}
