package com.example.SCHapi.model.repository.Quarto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;


public interface TipoQuartoRepository extends JpaRepository<TipoQuarto, Long> {
    
}