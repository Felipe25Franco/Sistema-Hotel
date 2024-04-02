package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.FinanceiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class FinanceiroService {

    private FinanceiroRepository repository;

    public FinanceiroService(FinanceiroRepository repository) {
        this.repository = repository;
    }

    public List<Financeiro> getFinanceiros() {
        return repository.findAll();
    }

    public Optional<Financeiro> getFinanceiroById(Long id) {
        return repository.findById(id);
    }
}