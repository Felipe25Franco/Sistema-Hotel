package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Reserva;
import com.example.SCHapi.model.entity.Servico;
import com.example.SCHapi.model.repository.ReservaRepository;
import com.example.SCHapi.model.repository.ServicoRepository;

import java.util.List;
import java.util.Optional;

public class ServicoService {
    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<Servico> getServico(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }
}
