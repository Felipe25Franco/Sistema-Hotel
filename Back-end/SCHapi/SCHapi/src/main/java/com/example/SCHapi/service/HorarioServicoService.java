package com.example.SCHapi.model.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.HorarioServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class HorarioServicoService {

    private HorarioServicoRepository repository;

    public HorarioServicoService(HorarioServicoRepository repository) {
        this.repository = repository;
    }

    public List<HorarioServico> getHorarioServicos() {
        return repository.findAll();
    }

    public Optional<HorarioServico> getHorarioServicoById(Long id) {
        return repository.findById(id);
    }
}