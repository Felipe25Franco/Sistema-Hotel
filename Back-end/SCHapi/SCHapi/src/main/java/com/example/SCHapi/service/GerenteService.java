package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.GerenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class GerenteService {

    private GerenteRepository repository;

    public GerenteService(GerenteRepository repository) {
        this.repository = repository;
    }

    public List<Gerente> getGerentes() {
        return repository.findAll();
    }

    public Optional<Gerente> getGerenteById(Long id) {
        return repository.findById(id);
    }
}