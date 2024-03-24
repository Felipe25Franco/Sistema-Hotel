package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.entity.StatusQuarto;
import com.example.SCHapi.model.repository.StatusHospedagemRepository;
import com.example.SCHapi.model.repository.StatusQuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StatusQuartoService {

    private StatusQuartoRepository repository;

    public StatusQuartoService(StatusQuartoRepository repository) {
        this.repository = repository;
    }

    public List<StatusQuarto> getStatusQuarto(){
        return repository.findAll();
    }

    public Optional<StatusQuarto> getStatusQuartoById(Long id) {
        return repository.findById(id);
    }
}
