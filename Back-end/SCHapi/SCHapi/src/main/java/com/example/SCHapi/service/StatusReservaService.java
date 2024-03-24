package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.StatusQuarto;
import com.example.SCHapi.model.entity.StatusReserva;
import com.example.SCHapi.model.repository.StatusQuartoRepository;
import com.example.SCHapi.model.repository.StatusReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StatusReservaService {

    private StatusReservaRepository repository;

    public StatusReservaService(StatusReservaRepository repository) {
        this.repository = repository;
    }

    public List<StatusReserva> getStatusReserva(){
        return repository.findAll();
    }

    public Optional<StatusReserva> getStatusReservaById(Long id) {
        return repository.findById(id);
    }
}
