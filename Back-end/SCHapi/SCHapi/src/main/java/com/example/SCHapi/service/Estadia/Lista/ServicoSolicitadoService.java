package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.repository.Estadia.Lista.ServicoSolicitadoRepository;
import com.example.SCHapi.model.repository.Servico.ServicoRepository;
import com.example.SCHapi.service.Servico.Lista.RelacaoHorarioServicoService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ServicoSolicitadoService {

    private ServicoSolicitadoRepository repository;
    private final RelacaoHorarioServicoService relacaoHorarioServicoService;

    public ServicoSolicitadoService(ServicoSolicitadoRepository repository, RelacaoHorarioServicoService relacaoHorarioServicoService) {
        this.repository = repository;
        this.relacaoHorarioServicoService = relacaoHorarioServicoService;
    }

    public List<ServicoSolicitado> getServicoSolicitados(){
        return repository.findAll();
    }

    public Optional<ServicoSolicitado> getServicoSolicitadoById(Long id) {
        return repository.findById(id);
    }

    public List<ServicoSolicitado> getServicoSolicitadoByHospedagem(Optional<Hospedagem> hospedagem) {
        return repository.findByHospedagem(hospedagem);
    }

    @Transactional
    public ServicoSolicitado salvar(ServicoSolicitado servicoSolicitado) {
        // validar(servicoSolicitado);
        return repository.save(servicoSolicitado);
    }

    @Transactional
    public void excluir(ServicoSolicitado servicoSolicitado) {
        Objects.requireNonNull(servicoSolicitado.getId());
        List<RelacaoHorarioServico> relacaoHorarioServicos = relacaoHorarioServicoService.getRelacaoHorarioServicoByServicoSolicitado(Optional.of(servicoSolicitado));
        // loop para cada elemento da lista deletar o relacaohorarioservico
        for (RelacaoHorarioServico relacaoHorarioServico : relacaoHorarioServicos) {
            relacaoHorarioServicoService.excluir(relacaoHorarioServico);
        }
        repository.delete(servicoSolicitado);
    }
}
