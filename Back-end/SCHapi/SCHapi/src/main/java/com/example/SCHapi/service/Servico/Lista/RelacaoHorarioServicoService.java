package com.example.SCHapi.service.Servico.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.repository.Servico.Lista.RelacaoHorarioServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    // essa query é pra retornar a lista de todos os tipos quarto
    public List<RelacaoHorarioServico> getRelacaoHorarioServicoByServicoSolicitado(Optional<ServicoSolicitado> servicosolicitado) {
        return repository.findByServicoSolicitado(servicosolicitado);
    }

    @Transactional
    public RelacaoHorarioServico salvar(RelacaoHorarioServico relacaoHorarioServico) {
        // validar(tipoCamaTipoQuarto);
        return repository.save(relacaoHorarioServico);
    }

    @Transactional
    public void excluir(RelacaoHorarioServico relacaoHorarioServico) {
        Objects.requireNonNull(relacaoHorarioServico.getId());
        repository.delete(relacaoHorarioServico);
    }
}
