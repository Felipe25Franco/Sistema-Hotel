package com.example.SCHapi.service;


import com.example.SCHapi.model.entity.TipoQuarto;

import com.example.SCHapi.model.repository.TipoQuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoQuartoService {

    private TipoQuartoRepository repository;
    public TipoQuartoService(TipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuarto> getTipoQuartos(){
        return repository.findAll();
    }

    public Optional<TipoQuarto> getTipoQuartoById(Long id) {
        return repository.findById(id);
    }
}
