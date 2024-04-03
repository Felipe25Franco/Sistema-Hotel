package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Servico;
import com.example.SCHapi.model.entity.ServicoSolicitado;
import com.example.SCHapi.model.repository.ServicoRepository;
import com.example.SCHapi.model.repository.ServicoSolicitadoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ServicoSolicitadoService {

    private ServicoSolicitadoRepository repository;

    public ServicoSolicitadoService(ServicoSolicitadoRepository repository) {
        this.repository = repository;
    }

    public List<ServicoSolicitado> getServicoSolicitado(){
        return repository.findAll();
    }

    public Optional<ServicoSolicitado> getServicoSolicitadoById(Long id) {
        return repository.findById(id);
    }
}
