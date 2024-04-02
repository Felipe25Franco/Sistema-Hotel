package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.TipoCama;
import com.example.SCHapi.model.entity.TipoQuartosReserva;
import com.example.SCHapi.model.repository.TipoCamaRepository;
import com.example.SCHapi.model.repository.TipoQuartosReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoQuartosReservaService {
    private TipoQuartosReservaRepository repository;

    public TipoQuartosReservaService(TipoQuartosReservaRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuartosReserva> getTipoQuartosReserva(){
        return repository.findAll();
    }

    public Optional<TipoQuartosReserva> getTipoQuartosReservaById(Long id) {
        return repository.findById(id);
    }
}
