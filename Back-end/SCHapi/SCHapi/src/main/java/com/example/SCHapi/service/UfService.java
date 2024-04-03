package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.TipoCama;
import com.example.SCHapi.model.entity.Uf;
import com.example.SCHapi.model.repository.TipoCamaRepository;
import com.example.SCHapi.model.repository.UfRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UfService {
    private UfRepository repository;

    public UfService(UfRepository repository) {
        this.repository = repository;
    }

    public List<Uf> getUfs(){
        return repository.findAll();
    }

    public Optional<Uf> getUfById(Long id) {
        return repository.findById(id);
    }
}
