package com.example.SCHapi.service.Quarto.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
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
        validar(comodidadeTipoQuarto);
        return repository.save(comodidadeTipoQuarto);
    }

    @Transactional
    public void excluir(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        Objects.requireNonNull(comodidadeTipoQuarto.getId());
        repository.delete(comodidadeTipoQuarto);
    }

    //fazer o validar dps
    public void validar(ComodidadeTipoQuarto comodidadeTipoQuarto) {
        
        if (comodidadeTipoQuarto.getComodidade() == null || comodidadeTipoQuarto.getComodidade().getId() == null || comodidadeTipoQuarto.getComodidade().getId() == 0) {
            throw new RegraNegocioException("Comodidade inválid0!!!!");
        }
        if (comodidadeTipoQuarto.getTipoQuarto() == null || comodidadeTipoQuarto.getTipoQuarto().getId() == null || comodidadeTipoQuarto.getTipoQuarto().getId() == 0) {
            throw new RegraNegocioException("Tipo Quarto inválid0!!!!");
        }
        if (comodidadeTipoQuarto.getQuantidade()<=0) {
            throw new RegraNegocioException("Quantidade deve ser maior que zero");
        }

        // FALTA LISTA DE QUARTOS

        List<ComodidadeTipoQuarto> comodidadeTipoQuartos = getComodidadeTipoQuartoByTipoQuarto(Optional.of(comodidadeTipoQuarto.getTipoQuarto()));
        if(comodidadeTipoQuartos.stream().anyMatch((x) -> x.getComodidade().getId()==comodidadeTipoQuarto.getComodidade().getId())) {
            throw new RegraNegocioException("Comodidades duplicadas. Selecione Comodidades distintas");
        }
    }
}