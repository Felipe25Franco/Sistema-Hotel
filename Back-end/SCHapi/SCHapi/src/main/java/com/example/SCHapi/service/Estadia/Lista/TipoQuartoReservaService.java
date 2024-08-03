package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.repository.Estadia.Lista.TipoQuartoReservaRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoQuartoReservaService {
    private TipoQuartoReservaRepository repository;

    public TipoQuartoReservaService(TipoQuartoReservaRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuartoReserva> getTipoQuartoReservas(){
        return repository.findAll();
    }

    public Optional<TipoQuartoReserva> getTipoQuartoReservaById(Long id) {
        return repository.findById(id);
    }

    // essa query é pra retornar a lista de todos os tipos quarto
    public List<TipoQuartoReserva> getTipoQuartoReservaByReserva(Optional<Reserva> reserva) {
        return repository.findByReserva(reserva);
    }

    @Transactional
    public TipoQuartoReserva salvar(TipoQuartoReserva tipoQuartoReserva) {
        // validar(tipoQuartoReserva);
        return repository.save(tipoQuartoReserva);
    }

    @Transactional
    public void excluir(TipoQuartoReserva tipoQuartoReserva) {
        Objects.requireNonNull(tipoQuartoReserva.getId());
        repository.delete(tipoQuartoReserva);
    }

    //fazer o validar dps
}
