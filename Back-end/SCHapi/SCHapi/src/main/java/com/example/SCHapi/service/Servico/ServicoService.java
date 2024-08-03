package com.example.SCHapi.service.Servico;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.repository.Servico.ServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicoService {
    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Servico salvar(Servico servico) {
        validar(servico);
        return repository.save(servico);
    }

    @Transactional
    public void excluir(Servico servico) {
        Objects.requireNonNull(servico.getId());
        repository.delete(servico);
    }


    public void validar(Servico servico) {

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
    }


}
