package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.example.SCHapi.model.repository.Estadia.Lista.RelacaoHorarioServicoRepository;

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
        validar(relacaoHorarioServico);
        return repository.save(relacaoHorarioServico);
    }

    @Transactional
    public void excluir(RelacaoHorarioServico relacaoHorarioServico) {
        Objects.requireNonNull(relacaoHorarioServico.getId());
        repository.delete(relacaoHorarioServico);
    }

    //fazer o validar dps
    public void validar(RelacaoHorarioServico relacaoHorarioServico) {
        
        if (relacaoHorarioServico.getHorarioServico() == null || relacaoHorarioServico.getHorarioServico().getId() == null || relacaoHorarioServico.getHorarioServico().getId() == 0) {
            throw new RegraNegocioException("Horário Serviço inválid0!!!!");
        }
        if (relacaoHorarioServico.getServicoSolicitado() == null || relacaoHorarioServico.getServicoSolicitado().getId() == null || relacaoHorarioServico.getServicoSolicitado().getId() == 0) {
            throw new RegraNegocioException("Serviço Solicitado inválid0!!!!");
        }
        if (relacaoHorarioServico.getQuantidadeVagas()<=0) {
            throw new RegraNegocioException("Quantidade deve ser maior que zero");
        }

        // FALTA LISTA DE QUARTOS
        List<RelacaoHorarioServico> relacaoHorarioServicos = getRelacaoHorarioServicoByServicoSolicitado(Optional.of(relacaoHorarioServico.getServicoSolicitado()));
        if(relacaoHorarioServicos.stream().anyMatch((x) -> x.getHorarioServico().getId()==relacaoHorarioServico.getHorarioServico().getId())) {
            throw new RegraNegocioException("Horário duplicado. Selecione Horários distintos");
        }
    }
}
