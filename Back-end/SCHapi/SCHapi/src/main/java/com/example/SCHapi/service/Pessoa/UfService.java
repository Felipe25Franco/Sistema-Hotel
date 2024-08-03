package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Pais;
import com.example.SCHapi.model.entity.Pessoa.Uf;

import com.example.SCHapi.model.repository.Pessoa.UfRepository;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UfService {
    private UfRepository repository;

    public UfService(UfRepository repository) {
        this.repository = repository;
    }

    public List<Uf> getUfs(){
        return repository.findAll();
    }

    public Optional<Uf> getUfById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Uf salvar(Uf uf) {
        validar(uf);
        return repository.save(uf);
    }

    @Transactional
    public void excluir(Uf uf) {
        Objects.requireNonNull(uf.getId());
        repository.delete(uf);
    }

    public void validar(Uf uf) {
        String titulo = uf.getTitulo();
        if (uf.getTitulo() == null || uf.getTitulo().trim().equals("") || !titulo.matches("[a-zA-ZÀ-ÿ\\s]+")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido, titulo deve ter apenas letras de A a Z.");
        }

        if (uf.getPais() == null || uf.getPais().getId() == null || uf.getPais().getId() == 0) {
            throw new RegraNegocioException("Pais inválido!!!!");
        }
        
    }
}
