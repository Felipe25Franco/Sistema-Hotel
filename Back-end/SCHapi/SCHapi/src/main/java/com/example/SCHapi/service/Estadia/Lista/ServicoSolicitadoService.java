package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.repository.Estadia.Lista.ServicoSolicitadoRepository;
import com.example.SCHapi.model.repository.Servico.ServicoRepository;

import javax.transaction.Transactional;

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

    // @Transactional
    // public ServicoSolicitado salvar(ServicoSolicitado servicoSolicitado) {
    //     validar(servicoSolicitado);
    //     return repository.save(servicoSolicitado);
    // }

    @Transactional
    public ServicoSolicitado salvarFull(ServicoSolicitado servicoSolicitado, List<RelacaoHorarioServico> relacaoHorarioServicos) {
        validar(servicoSolicitado, relacaoHorarioServicos);
        // System.out.println(servicoSolicitado.getId());
        // System.out.println(servicoSolicitado.getValorTotal());
        // System.out.println(servicoSolicitado.getHospedagem().getId());
        // System.out.println(servicoSolicitado.getServico().getId());
        //servicoSolicitado.getRelacaoHorarioServico().stream().forEach((x)->{System.out.println(x.getId());});
        servicoSolicitado = repository.save(servicoSolicitado);
        // excluir a lista antiga de TipoQUarto
        List<RelacaoHorarioServico> relacaoHorarioServicosDel = relacaoHorarioServicoService.getRelacaoHorarioServicoByServicoSolicitado(Optional.of(servicoSolicitado));
        // loop para cada elemento da lista deletar o relacaohorarioservico
        for (RelacaoHorarioServico relacaoHorarioServico : relacaoHorarioServicosDel) {
            //relacaoHorarioServicoService.excluir(relacaoHorarioServico);
            if (!relacaoHorarioServicos.stream().anyMatch((o)-> relacaoHorarioServico.getId().equals(o.getId()))){
                relacaoHorarioServicoService.excluir(relacaoHorarioServico);
            }
        }
        // Salvar os novos TipoQUarto
        for (RelacaoHorarioServico relacaoHorarioServico : relacaoHorarioServicos) {
            relacaoHorarioServico.setServicoSolicitado(servicoSolicitado);
            System.out.println(" id");
            System.out.println(relacaoHorarioServico.getHorarioServico()==null);
            relacaoHorarioServicoService.salvar(relacaoHorarioServico);
        }
        return servicoSolicitado;
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

    public void validar(ServicoSolicitado servicoSolicitado, List<RelacaoHorarioServico> relacaoHorarioServicos) {
        
        if (servicoSolicitado.getServico() == null || servicoSolicitado.getServico().getId() == null || servicoSolicitado.getServico().getId() == 0) {
            throw new RegraNegocioException("Serviço inválid0!!!!");
        }
        if (servicoSolicitado.getHospedagem() == null || servicoSolicitado.getHospedagem().getId() == null || servicoSolicitado.getHospedagem().getId() == 0) {
            throw new RegraNegocioException("Hospedagem inválid0!!!!");
        }

        // FALTA LISTA DE QUARTOS

        if (relacaoHorarioServicos.isEmpty()) {
            throw new RegraNegocioException("Selecione pelo menos um horário válido");
        }

        List<ServicoSolicitado> servicosolicitados = getServicoSolicitadoByHospedagem(Optional.of(servicoSolicitado.getHospedagem()));
        if(servicosolicitados.stream().anyMatch((x) -> servicoSolicitado.getServico().getId().equals(x.getServico().getId())&&!servicoSolicitado.getId().equals(x.getId()))) {
            throw new RegraNegocioException("Serviço já presente na lista de serviços solicitados");
        }
    }
}
