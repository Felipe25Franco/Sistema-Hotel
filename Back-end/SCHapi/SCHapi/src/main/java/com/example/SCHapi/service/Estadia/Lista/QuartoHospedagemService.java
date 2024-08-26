package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.repository.Estadia.Lista.QuartoHospedagemRepository;
import com.example.SCHapi.model.repository.Quarto.QuartoRepository;
import com.example.SCHapi.service.Estadia.HospedagemService;

import javax.transaction.Transactional;

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
        validar(quartoHospedagem);
        return repository.save(quartoHospedagem);
    }

    @Transactional
    public void excluir(QuartoHospedagem quartoHospedagem) {
        Objects.requireNonNull(quartoHospedagem.getId());
        repository.delete(quartoHospedagem);
    }

    @Transactional
    public void excluirList(List<QuartoHospedagem> quartoHospedagems) {
        //Objects.requireNonNull(quartoHospedagem.getId());
        repository.deleteAllById(quartoHospedagems.stream().map(QuartoHospedagem::getId).toList());
    }

    public void validar(QuartoHospedagem quartoHospedagem) {

        if (quartoHospedagem.getQuarto() == null || quartoHospedagem.getQuarto().getId() == null || quartoHospedagem.getQuarto().getId() == 0) {
            throw new RegraNegocioException("Quarto inválid0!!!!");
        }
        if (quartoHospedagem.getHospedagem() == null || quartoHospedagem.getHospedagem().getId() == null || quartoHospedagem.getHospedagem().getId() == 0) {
            throw new RegraNegocioException("Hospedagem inválid0!!!!");
        }

        List<QuartoHospedagem> quartoHospedagems = getQuartoHospedagemByHospedagem(Optional.of(quartoHospedagem.getHospedagem()));
        if(quartoHospedagems.stream().anyMatch((x) -> x.getQuarto().getId()==quartoHospedagem.getQuarto().getId())) {
            throw new RegraNegocioException("Quartos duplicado. Selecione quartos distintos");
        }
    }
}
