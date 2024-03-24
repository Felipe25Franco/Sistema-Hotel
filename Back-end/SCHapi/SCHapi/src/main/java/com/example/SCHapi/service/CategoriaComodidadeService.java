package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.CategoriaComodidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class CategoriaComodidadeService {

    private CategoriaComodidadeRepository repository;

    public CategoriaComodidadeService(CategoriaComodidadeRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaComodidade> getCategoriaComodidades() {
        return repository.findAll();
    }

    public Optional<CategoriaComodidade> getCategoriaComodidadeById(Long id) {
        return repository.findById(id);
    }
}