package com.example.SCHapi.service.Estadia;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.repository.Estadia.AvaliacaoHospedagemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class AvaliacaoHospedagemService {

    private AvaliacaoHospedagemRepository repository;

    public AvaliacaoHospedagemService(AvaliacaoHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoHospedagem> getAvaliacaoHospedagens() {
        return repository.findAll();
    }

    public Optional<AvaliacaoHospedagem> getAvaliacaoHospedagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AvaliacaoHospedagem salvar(AvaliacaoHospedagem avaliacaoHospedagem) {
        validar(avaliacaoHospedagem);
        return repository.save(avaliacaoHospedagem);
    }

    @Transactional
    public AvaliacaoHospedagem salvarSemValidar(AvaliacaoHospedagem avaliacaoHospedagem) {
        //validar(avaliacaoHospedagem);
        return repository.save(avaliacaoHospedagem);
    }

    @Transactional
    public void excluir(AvaliacaoHospedagem avaliacaoHospedagem) {
        Objects.requireNonNull(avaliacaoHospedagem.getId());
        repository.delete(avaliacaoHospedagem);
    }

    public void validar(AvaliacaoHospedagem avaliacaoHospedagem) {

        Float nota = avaliacaoHospedagem.getNota();


        // if (avaliacaoHospedagem.getComentario() == null || avaliacaoHospedagem.getComentario().trim().equals("")){
        //     throw new RegraNegocioException("Comentario Invalido!!! Insira um comentario valido.");
        // }
        if (nota == null || (nota < 0 || nota > 5)) {
            throw new RegraNegocioException("A nota de hospedagem deve estar entre 0 e 5 e não pode estar nula, nota inválida.");
        }

    }


}