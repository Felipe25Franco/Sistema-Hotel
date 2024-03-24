package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.RecusosHumanos;
import com.example.SCHapi.model.entity.RelacaoHorariosServico;
import com.example.SCHapi.model.repository.RecusosHumanosRepository;
import com.example.SCHapi.model.repository.RelacaoHorariosServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelacaoHorariosServicoService {
    private RelacaoHorariosServicoRepository repository;

    public RelacaoHorariosServicoService(RelacaoHorariosServicoRepository repository) {
        this.repository = repository;
    }

    public List<RelacaoHorariosServico> getRelacaoHorariosServico(){
        return repository.findAll();
    }

    public Optional<RelacaoHorariosServico> getRelacaoHorariosServicoById(Long id) {
        return repository.findById(id);
    }
}
