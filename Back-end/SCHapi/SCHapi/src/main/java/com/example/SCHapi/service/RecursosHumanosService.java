package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Recepcionista;
import com.example.SCHapi.model.entity.RecusosHumanos;
import com.example.SCHapi.model.repository.RecepcionistaRepository;
import com.example.SCHapi.model.repository.RecusosHumanosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursosHumanosService {
    private RecusosHumanosRepository repository;

    public RecursosHumanosService(RecusosHumanosRepository repository) {
        this.repository = repository;
    }

    public List<RecusosHumanos> getRecursosHumanos(){
        return repository.findAll();
    }

    public Optional<RecusosHumanos> getRecursosHumanosById(Long id) {
        return  repository.findById(id);
    }
}
