package com.example.SCHapi.service.Servico;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.StatusServico;
import com.example.SCHapi.model.repository.Estadia.ReservaRepository;
import com.example.SCHapi.model.repository.Servico.StatusServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class StatusServicoService {

    private StatusServicoRepository repository;

    public StatusServicoService(StatusServicoRepository repository) {
        this.repository = repository;
    }

    public List<StatusServico> getStatusServico(){
        return repository.findAll();
    }

    public Optional<StatusServico> getStatusServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public StatusServico salvar(StatusServico statusServico) {
        validar(statusServico);
        return repository.save(statusServico);
    }

    @Transactional
    public void excluir(StatusServico statusServico) {
        Objects.requireNonNull(statusServico.getId());
        repository.delete(statusServico);
    }

    public void validar(StatusServico statusServico) {
       
        if (statusServico.getTitulo() == null || statusServico.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo Invalido!!! Insira uma titulo valido.");
        }
    }
}
