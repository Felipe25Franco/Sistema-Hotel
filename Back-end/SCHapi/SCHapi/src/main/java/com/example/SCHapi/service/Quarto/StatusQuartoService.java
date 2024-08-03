package com.example.SCHapi.service.Quarto;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;

import com.example.SCHapi.model.repository.Quarto.StatusQuartoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class StatusQuartoService {

    private StatusQuartoRepository repository;

    public StatusQuartoService(StatusQuartoRepository repository) {
        this.repository = repository;
    }

    public List<StatusQuarto> getStatusQuarto(){
        return repository.findAll();
    }

    public Optional<StatusQuarto> getStatusQuartoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public StatusQuarto salvar(StatusQuarto statusQuarto) {
        validar(statusQuarto);
        return repository.save(statusQuarto);
    }

    @Transactional
    public void excluir(StatusQuarto statusQuarto) {
        Objects.requireNonNull(statusQuarto.getId());
        repository.delete(statusQuarto);
    }

    public void validar(StatusQuarto statusQuarto) {
       
        if (statusQuarto.getTitulo() == null || statusQuarto.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo Invalido!!! Insira uma titulo valido.");
        }
    }
}
