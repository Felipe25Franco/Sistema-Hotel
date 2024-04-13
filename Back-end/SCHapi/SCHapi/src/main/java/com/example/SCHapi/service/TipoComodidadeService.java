package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.TipoComodidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class TipoComodidadeService {

    private TipoComodidadeRepository repository;

    public TipoComodidadeService(TipoComodidadeRepository repository) {
        this.repository = repository;
    }

    public List<TipoComodidade> getTipoComodidades() {
        return repository.findAll();
    }

    public Optional<TipoComodidade> getTipoComodidadeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoComodidade salvar(TipoComodidade tipoComodidade) {
        validar(tipoComodidade);
        return repository.save(tipoComodidade);
    }

    public void validar(TipoComodidade tipoComodidade) {
        if (tipoComodidade.getDescricao() == null || tipoComodidade.getDescricao() == "") {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (tipoComodidade.getCategoria() == null || tipoComodidade.getCategoria() == "") {
            throw new RegraNegocioException("Categoria Invalida!!! Insira uma categoria valida.");
        }
    }
}