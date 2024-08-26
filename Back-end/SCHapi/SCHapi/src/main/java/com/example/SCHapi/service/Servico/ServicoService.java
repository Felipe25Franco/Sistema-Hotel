package com.example.SCHapi.service.Servico;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.entity.Servico.TipoServico;
import com.example.SCHapi.model.repository.Servico.ServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicoService {
    private ServicoRepository repository;
    private HorarioServicoService horarioServicoService;

    public ServicoService(ServicoRepository repository, HorarioServicoService horarioServicoService) {
        this.repository = repository;
        this.horarioServicoService = horarioServicoService;
    }

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }

    public List<Servico> getServicoByTipoServico(Optional<TipoServico> TipoServico) {
        return repository.findByTipoServico(TipoServico);
    }

    // @Transactional
    // public Servico salvar(Servico servico) {
    //     validar(servico);
    //     return repository.save(servico);
    // }

    @Transactional
    public Servico salvarFull(Servico servico, List<HorarioServico> horarioServicos) {
        validar(servico, horarioServicos);
        servico = repository.save(servico);
        // excluir a lista antiga de TipoQUarto
        List<HorarioServico> horarioServicosDel = horarioServicoService.getHorarioServicoByServico(Optional.of(servico));
        // loop para cada elemento da lista deletar o horarioservico
        for (HorarioServico horarioServico : horarioServicosDel) {
            //horarioServicoService.getHorarioServicos().stream().filter((o)->{horarioServicos.stream().map(HorarioServico::getId).findAny(Optional.of(o.getId())0});
            if (!horarioServicos.stream().anyMatch((o)-> horarioServico.getId().equals(o.getId()))){
                horarioServicoService.excluir(horarioServico);
            }
        }
        // Salvar os novos TipoQUarto
        for (HorarioServico horarioServico : horarioServicos) {
            horarioServico.setServico(servico);
            horarioServicoService.salvar(horarioServico);
            // if (horarioServico.getId()!=null) {
            //     if (horarioServicoService.getHorarioServicoById(horarioServico.getId()).isPresent()) {
            //         horarioServicoService.salvar(horarioServico);
            //     }
            // }
        }
        return servico;
    }

    @Transactional
    public void excluir(Servico servico) {
        Objects.requireNonNull(servico.getId());
        List<HorarioServico> horarioServicos = horarioServicoService.getHorarioServicoByServico(Optional.of(servico));
        //horarioServicos.stream().map(x->{horarioServicoService.excluir(x);});
        // loop para cada elemento da lista deletar o horarioservico
        for (HorarioServico horarioServico : horarioServicos) {
            horarioServicoService.excluir(horarioServico);
        }
        repository.delete(servico);
    }


    public void validar(Servico servico, List<HorarioServico> horarioServicos) {

        Float valorPorHorario = servico.getValorPorHorario();

        if (servico.getTitulo() == null || servico.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (servico.getDescricao() == null || servico.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (valorPorHorario <= 0 || valorPorHorario == null) {
            throw new RegraNegocioException("O valor do horario por serviço tem que ser maior que 0.");
        }
        if (servico.getTipoReserva() == null || servico.getTipoReserva().trim().equals("")) {
            throw new RegraNegocioException("Tipo de Reserva invalida Invalida!!! Insira uma tipo valido.");
        }
        if (servico.getTipoServico() == null || servico.getTipoServico().getId() == null || servico.getTipoServico().getId() == 0) {
            throw new RegraNegocioException("Tipo de serviço inválido!!!!");
        }
        if (servico.getHotel() == null || servico.getHotel().getId() == null || servico.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválido!!!!");
        }
        if (servico.getStatusServico() == null || servico.getStatusServico().getId() == null || servico.getStatusServico().getId() == 0) {
            throw new RegraNegocioException("Status de serviço inválido!!!!");
        }

        List<Servico> servicos = getServicoByTipoServico(Optional.of(servico.getTipoServico()));
        if(servicos.stream().anyMatch((x) -> x.getTitulo().trim().equals(servico.getTitulo().trim())&&x.getHotel().getId()==servico.getHotel().getId()&&!servico.getId().equals(x.getId()))) {
            throw new RegraNegocioException("Título já cadastrado para a categoria e hotel selecionado");
        }
    }


}
