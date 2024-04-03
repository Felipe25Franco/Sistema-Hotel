package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.ProdutoSolicitadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ProdutoSolicitadoService {

    private ProdutoSolicitadoRepository repository;

    public ProdutoSolicitadoService(ProdutoSolicitadoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoSolicitado> getProdutoSolicitados() {
        return repository.findAll();
    }

    public Optional<ProdutoSolicitado> getProdutoSolicitadoById(Long id) {
        return repository.findById(id);
    }
}