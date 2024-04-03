package com.example.SCHapi.service;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Servico;
import com.example.SCHapi.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }
}
