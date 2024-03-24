package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.ComodidadeTipoQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ComodidadeTipoQuartoService {

    private ComodidadeTipoQuartoRepository repository;

    public ComodidadeTipoQuartoService(ComodidadeTipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<ComodidadeTipoQuarto> getComodidadeTipoQuartos() {
        return repository.findAll();
    }

    public Optional<ComodidadeTipoQuarto> getComodidadeTipoQuartoById(Long id) {
        return repository.findById(id);
    }
}