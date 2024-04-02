package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Quarto;
import com.example.SCHapi.model.entity.QuartosHospedagem;
import com.example.SCHapi.model.repository.QuartoRepository;
import com.example.SCHapi.model.repository.QuartosHospedagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartosHospedagemService {

    private QuartosHospedagemRepository repository;

    public QuartosHospedagemService(QuartosHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<QuartosHospedagem> getQuartosHospedagem(){
        return repository.findAll();
    }

    public Optional<QuartosHospedagem> getQuartosHospedagemById(Long id) {
        return  repository.findById(id);
    }
}
