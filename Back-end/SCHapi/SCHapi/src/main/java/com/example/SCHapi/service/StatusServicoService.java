package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Reserva;
import com.example.SCHapi.model.entity.StatusServico;
import com.example.SCHapi.model.repository.ReservaRepository;
import com.example.SCHapi.model.repository.StatusServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StatusServicoService {

    private StatusServicoRepository repository;

    public StatusServicoService(StatusServicoRepository repository) {
        this.repository = repository;
    }

    public List<StatusServico> getStatusServico(){
        return repository.findAll();
    }

    public Optional<StatusServico> getStatusServicoById(Long id) {
        return repository.findById(id);
    }
}
