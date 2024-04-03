package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.Reserva;
import com.example.SCHapi.model.entity.TipoQuartoReserva;
import com.example.SCHapi.model.repository.TipoQuartoReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoQuartoReservaService {
    private TipoQuartoReservaRepository repository;

    public TipoQuartoReservaService(TipoQuartoReservaRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuartoReserva> getTipoQuartoReserva(){
        return repository.findAll();
    }

    public Optional<TipoQuartoReserva> getTipoQuartoReservaById(Long id) {
        return repository.findById(id);
    }

    // essa query é pra retornar a lista de todos os tipos quarto
    public List<TipoQuartoReserva> getTipoQuartoReservaByReserva(Optional<Reserva> reserva) {
        return repository.findByReserva(reserva);
    }
}
