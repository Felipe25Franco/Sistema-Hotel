package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Quarto;
import com.example.SCHapi.model.entity.QuartoHospedagem;
import com.example.SCHapi.model.repository.QuartoRepository;
import com.example.SCHapi.model.repository.QuartoHospedagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoHospedagemService {

    private QuartoHospedagemRepository repository;

    public QuartoHospedagemService(QuartoHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<QuartoHospedagem> getQuartoHospedagens(){
        return repository.findAll();
    }

    public Optional<QuartoHospedagem> getQuartoHospedagemById(Long id) {
        return  repository.findById(id);
    }
}
