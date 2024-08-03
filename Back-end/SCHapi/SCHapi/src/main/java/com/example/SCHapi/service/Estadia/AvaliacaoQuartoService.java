package com.example.SCHapi.service.Estadia;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.repository.Estadia.AvaliacaoQuartoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class AvaliacaoQuartoService {

    private AvaliacaoQuartoRepository repository;

    public AvaliacaoQuartoService(AvaliacaoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoQuarto> getAvaliacaoQuartos() {
        return repository.findAll();
    }

    public Optional<AvaliacaoQuarto> getAvaliacaoQuartoById(Long id) {
        return repository.findById(id);
    }

    // essa query é pra retornar a lista de todos os tipos quarto
    public List<AvaliacaoQuarto> getAvaliacaoQuartoByHospedagem(Optional<Hospedagem> hospedagem) {
        return repository.findByHospedagem(hospedagem);
    }

    @Transactional
    public AvaliacaoQuarto salvar(AvaliacaoQuarto avaliacaoQuarto) {
        validar(avaliacaoQuarto);
        return repository.save(avaliacaoQuarto);
    }

    @Transactional
    public AvaliacaoQuarto salvarSemValidar(AvaliacaoQuarto avaliacaoQuarto) {
        //validar(avaliacaoQuarto);
        return repository.save(avaliacaoQuarto);
    }

    @Transactional
    public void excluir(AvaliacaoQuarto avaliacaoQuarto) {
        Objects.requireNonNull(avaliacaoQuarto.getId());
        repository.delete(avaliacaoQuarto);
    }


    public void validar(AvaliacaoQuarto avaliacaoQuarto) {

        Float nota = avaliacaoQuarto.getNota();

        // if (avaliacaoQuarto.getComentario() == null || avaliacaoQuarto.getComentario().trim().equals("")){
        //     throw new RegraNegocioException("Comentario invalido Invalido!!! Insira um comentario valido.");
        // }
        if (avaliacaoQuarto.getNota() < 0 ||  avaliacaoQuarto.getNota() > 5 || nota == null) {
            throw new RegraNegocioException("Nota Invalida!!! Insira uma nota valida, entre 0 e 5.");
        }
        if (avaliacaoQuarto.getTipoQuarto() == null || avaliacaoQuarto.getTipoQuarto().getId() == null || avaliacaoQuarto.getTipoQuarto().getId() == 0) {
            throw new RegraNegocioException("Tipo quarto inválido!!!!");
        }
        if (avaliacaoQuarto.getHospedagem() == null || avaliacaoQuarto.getHospedagem().getId() == null || avaliacaoQuarto.getHospedagem().getId() == 0) {
            throw new RegraNegocioException("Hospedagem inválida!!!!");
        }
    }


}