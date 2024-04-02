package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class HospedagemService {

    private HospedagemRepository repository;

    public HospedagemService(HospedagemRepository repository) {
        this.repository = repository;
    }

    public List<Hospedagem> getHospedagens() {
        return repository.findAll();
    }

    public Optional<Hospedagem> getHospedagemById(Long id) {
        return repository.findById(id);
    }
}