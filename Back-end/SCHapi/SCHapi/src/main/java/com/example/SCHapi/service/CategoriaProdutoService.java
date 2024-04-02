package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.CategoriaProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class CategoriaProdutoService {

    private CategoriaProdutoRepository repository;

    public CategoriaProdutoService(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaProduto> getCategoriaProdutos() {
        return repository.findAll();
    }

    public Optional<CategoriaProduto> getCategoriaProdutoById(Long id) {
        return repository.findById(id);
    }
}