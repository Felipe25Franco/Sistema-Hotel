package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.TipoCama;
import com.example.SCHapi.model.entity.Usuario;
import com.example.SCHapi.model.repository.TipoCamaRepository;
import com.example.SCHapi.model.repository.UsuarioRepository;
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
