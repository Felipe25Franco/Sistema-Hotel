package com.example.SCHapi.service;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.AdministradorGeralRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class AdministradorGeralService {

    private AdministradorGeralRepository repository;

    public AdministradorGeralService(AdministradorGeralRepository repository) {
        this.repository = repository;
    }

    public List<AdministradorGeral> getAdministradoresGeral() {
        return repository.findAll();
    }

    public Optional<AdministradorGeral> getAdministradorGeralById(Long id) {
        return repository.findById(id);
    }
}