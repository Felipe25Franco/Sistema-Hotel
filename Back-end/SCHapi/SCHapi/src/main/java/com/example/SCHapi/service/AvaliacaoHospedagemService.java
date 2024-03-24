package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.AvaliacaoHospedagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class AvaliacaoHospedagemService {

    private AvaliacaoHospedagemRepository repository;

    public AvaliacaoHospedagemService(AvaliacaoHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoHospedagem> getAvaliacaoHospedagens() {
        return repository.findAll();
    }

    public Optional<AvaliacaoHospedagem> getAvaliacaoHospedagemById(Long id) {
        return repository.findById(id);
    }
}