package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.model.entity.Usuario;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.model.repository.Pessoa.UsuarioRepository;
import com.example.SCHapi.model.repository.Quarto.TipoCamaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> getUsuario(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return repository.findById(id);
    }
}
