package com.example.SCHapi.service.Quarto.Lista;

import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.repository.Quarto.Lista.ComodidadeTipoQuartoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ComodidadeTipoQuartoService {

    private ComodidadeTipoQuartoRepository repository;

    public ComodidadeTipoQuartoService(ComodidadeTipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<ComodidadeTipoQuarto> getComodidadeTipoQuartos() {
        return repository.findAll();
    }

    public Optional<ComodidadeTipoQuarto> getComodidadeTipoQuartoById(Long id) {
        return repository.findById(id);
    }

    public List<ComodidadeTipoQuarto> getComodidadeTipoQuartoByTipoQuarto(Optional<TipoQuarto> tipoQuarto) {
        return repository.findByTipoQuarto(tipoQuarto);
    }

    @Transactional
    public ComodidadeTipoQuarto salvar(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        // validar(comodidadeTipoQuarto);
        return repository.save(comodidadeTipoQuarto);
    }

    @Transactional
    public void excluir(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        Objects.requireNonNull(comodidadeTipoQuarto.getId());
        repository.delete(comodidadeTipoQuarto);
    }
}