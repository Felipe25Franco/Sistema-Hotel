package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;

import com.example.SCHapi.model.entity.Pessoa.Pais;
import com.example.SCHapi.model.repository.Pessoa.PaisRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class PaisService {

    private PaisRepository repository;

    public PaisService(PaisRepository repository) {
        this.repository = repository;
    }

    public List<Pais> getPaises() {
        return repository.findAll();
    }

    public Optional<Pais> getPaisById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Pais salvar(Pais pais) {
        validar(pais);
        return repository.save(pais);
    }

    @Transactional
    public void excluir(Pais pais) {
        Objects.requireNonNull(pais.getId());
        repository.delete(pais);
    }

    public void validar(Pais pais) {
        String titulo = pais.getTitulo();

        if (pais.getTitulo() == null || pais.getTitulo().trim().equals("") || !titulo.matches("[a-zA-ZÀ-ÿ\\s]+")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido, titulo deve ter apenas letras de A a Z.");
        }
    }
}