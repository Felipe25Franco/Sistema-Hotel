package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.RelacaoHorarioServico;
import com.example.SCHapi.model.repository.RelacaoHorarioServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelacaoHorarioServicoService {
    private RelacaoHorarioServicoRepository repository;

    public RelacaoHorarioServicoService(RelacaoHorarioServicoRepository repository) {
        this.repository = repository;
    }

    public List<RelacaoHorarioServico> getRelacaoHorariosServico(){
        return repository.findAll();
    }

    public Optional<RelacaoHorarioServico> getRelacaoHorariosServicoById(Long id) {
        return repository.findById(id);
    }
}
