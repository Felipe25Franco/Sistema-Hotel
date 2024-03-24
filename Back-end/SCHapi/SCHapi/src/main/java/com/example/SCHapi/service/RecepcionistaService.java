package com.example.SCHapi.service;


import com.example.SCHapi.model.entity.Recepcionista;

import com.example.SCHapi.model.repository.RecepcionistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecepcionistaService {
    private RecepcionistaRepository repository;

    public RecepcionistaService(RecepcionistaRepository repository) {
        this.repository = repository;
    }

    public List<Recepcionista> getRecepcionista(){
        return repository.findAll();
    }

    public Optional<Recepcionista> getRecepcionistaById(Long id) {
        return  repository.findById(id);
    }
}
