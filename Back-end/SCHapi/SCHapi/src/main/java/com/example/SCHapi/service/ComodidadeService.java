package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.ComodidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ComodidadeService {

    private ComodidadeRepository repository;

    public ComodidadeService(ComodidadeRepository repository) {
        this.repository = repository;
    }

    public List<Comodidade> getComodidades() {
        return repository.findAll();
    }

    public Optional<Comodidade> getComodidadeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Comodidade salvar(Comodidade comodidade) {
        validar(comodidade);
        return repository.save(comodidade);
    }

    public void validar(Comodidade comodidade) {



        if (comodidade.getTitulo() == null || comodidade.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (comodidade.getDescricao() == null || comodidade.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (comodidade.getTipoComodidade() == null || comodidade.getTipoComodidade().getId() == null || comodidade.getTipoComodidade().getId() == 0) {
            throw new RegraNegocioException("Tipo de comodidade inválida!!!!");
        }
    }
}