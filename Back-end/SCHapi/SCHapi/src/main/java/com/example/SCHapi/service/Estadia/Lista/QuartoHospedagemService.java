package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.repository.Estadia.Lista.QuartoHospedagemRepository;
import com.example.SCHapi.model.repository.Quarto.QuartoRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuartoHospedagemService {

    private QuartoHospedagemRepository repository;

    public QuartoHospedagemService(QuartoHospedagemRepository repository) {
        this.repository = repository;
    }

    public List<QuartoHospedagem> getQuartoHospedagens(){
        return repository.findAll();
    }

    public Optional<QuartoHospedagem> getQuartoHospedagemById(Long id) {
        return  repository.findById(id);
    }

    public List<QuartoHospedagem> getQuartoHospedagemByHospedagem(Optional<Hospedagem> hospedagem) {
        return repository.findByHospedagem(hospedagem);
    }

    @Transactional
    public QuartoHospedagem salvar(QuartoHospedagem quartoHospedagem) {
        // validar(quartoHospedagem);
        return repository.save(quartoHospedagem);
    }

    @Transactional
    public void excluir(QuartoHospedagem quartoHospedagem) {
        Objects.requireNonNull(quartoHospedagem.getId());
        repository.delete(quartoHospedagem);
    }
}
