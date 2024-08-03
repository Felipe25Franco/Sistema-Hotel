package com.example.SCHapi.service.Estadia;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Estadia.StatusReserva;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.repository.Estadia.StatusHospedagemRepository;
import com.example.SCHapi.model.repository.Estadia.Lista.ServicoSolicitadoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class StatusHospedagemService {
    private StatusHospedagemRepository repository;

    public StatusHospedagemService(StatusHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<StatusHospedagem> getStatusHospedagem(){
        return repository.findAll();
    }

    public Optional<StatusHospedagem> getStatusHospedagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public StatusHospedagem salvar(StatusHospedagem statusHospedagem) {
        validar(statusHospedagem);
        return repository.save(statusHospedagem);
    }

    @Transactional
    public void excluir(StatusHospedagem statusHospedagem) {
        Objects.requireNonNull(statusHospedagem.getId());
        repository.delete(statusHospedagem);
    }

    public void validar(StatusHospedagem statusHospedagem) {
       
        if (statusHospedagem.getTitulo() == null || statusHospedagem.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo Invalido!!! Insira uma titulo valido.");
        }
    }
}
