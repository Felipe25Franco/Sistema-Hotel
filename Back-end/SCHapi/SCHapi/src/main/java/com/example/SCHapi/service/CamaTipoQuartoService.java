package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.CamaTipoQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class CamaTipoQuartoService {

    private CamaTipoQuartoRepository repository;

    public CamaTipoQuartoService(CamaTipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<CamaTipoQuarto> getCamaTipoQuartos() {
        return repository.findAll();
    }

    public Optional<CamaTipoQuarto> getCamaTipoQuartoById(Long id) {
        return repository.findById(id);
    }
}