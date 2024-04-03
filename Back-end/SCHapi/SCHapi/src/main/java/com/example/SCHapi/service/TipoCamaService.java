package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.entity.TipoCama;
import com.example.SCHapi.model.repository.StatusHospedagemRepository;
import com.example.SCHapi.model.repository.TipoCamaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipoCamaService {

    private TipoCamaRepository repository;

    public TipoCamaService(TipoCamaRepository repository) {
        this.repository = repository;
    }

    public List<TipoCama> getTipoCamas(){
        return repository.findAll();
    }

    public Optional<TipoCama> getTipoCamaById(Long id) {
        return repository.findById(id);
    }
}
