package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.ServicoSolicitado;
import com.example.SCHapi.model.entity.StatusHospedagem;
import com.example.SCHapi.model.repository.ServicoSolicitadoRepository;
import com.example.SCHapi.model.repository.StatusHospedagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StatusHospedagemService {
    private StatusHospedagemRepository repository;

    public StatusHospedagemService(StatusHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<StatusHospedagem> getStatusHospedagem(){
        return repository.findAll();
    }

    public Optional<StatusHospedagem> getStatusHospedagemById(Long id) {
        return repository.findById(id);
    }
}
