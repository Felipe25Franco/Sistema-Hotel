package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.model.entity.Pessoa.Pessoa;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.model.repository.Pessoa.PessoaRepository;
import com.example.SCHapi.model.repository.Quarto.TipoCamaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    private PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> getUsuario(){
        return repository.findAll();
    }

    public Optional<Pessoa> getUsuarioById(Long id) {
        return repository.findById(id);
    }
}
