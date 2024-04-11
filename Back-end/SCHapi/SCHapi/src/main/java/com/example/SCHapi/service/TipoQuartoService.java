package com.example.SCHapi.service;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.TipoQuarto;

import com.example.SCHapi.model.repository.TipoQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoQuartoService {

    private TipoQuartoRepository repository;
    public TipoQuartoService(TipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuarto> getTipoQuartos(){
        return repository.findAll();
    }

    public Optional<TipoQuarto> getTipoQuartoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoQuarto salvar(TipoQuarto tipoQuarto) {
        validar(tipoQuarto);
        return repository.save(tipoQuarto);
    }

    public void validar(TipoQuarto tipoQuarto) {
        if (tipoQuarto.getDescricao() == null || tipoQuarto.getDescricao() == "") {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (tipoQuarto.getTitulo() == null || tipoQuarto.getTitulo() == "") {
            throw new RegraNegocioException("Titulo Invalido!!! Insira uma titulo valido.");
        }
       
    }

}
