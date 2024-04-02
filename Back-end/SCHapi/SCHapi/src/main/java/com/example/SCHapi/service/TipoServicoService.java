package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.TipoCama;
import com.example.SCHapi.model.entity.TipoServico;
import com.example.SCHapi.model.repository.TipoCamaRepository;
import com.example.SCHapi.model.repository.TipoServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicoService {
    private TipoServicoRepository repository;

    public TipoServicoService(TipoServicoRepository repository) {
        this.repository = repository;
    }

    public List<TipoServico> getTipoServico(){
        return repository.findAll();
    }

    public Optional<TipoServico> getTipoServicoById(Long id) {
        return repository.findById(id);
    }
}
