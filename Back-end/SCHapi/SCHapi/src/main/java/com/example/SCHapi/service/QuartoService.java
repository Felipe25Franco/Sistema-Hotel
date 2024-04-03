package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Quarto;
import com.example.SCHapi.model.repository.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class QuartoService {

    private QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> getQuartos(){
        return repository.findAll();
    }

    public Optional<Quarto> getQuartoById(Long id) {
        return  repository.findById(id);
    }

}





