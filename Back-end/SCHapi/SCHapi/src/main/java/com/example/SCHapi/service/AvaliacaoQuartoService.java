package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.AvaliacaoQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class AvaliacaoQuartoService {

    private AvaliacaoQuartoRepository repository;

    public AvaliacaoQuartoService(AvaliacaoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoQuarto> getAvaliacaoQuartos() {
        return repository.findAll();
    }

    public Optional<AvaliacaoQuarto> getAvaliacaoQuartoById(Long id) {
        return repository.findById(id);
    }
}