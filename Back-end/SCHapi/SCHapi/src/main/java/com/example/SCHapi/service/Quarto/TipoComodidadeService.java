package com.example.SCHapi.service.Quarto;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.repository.Quarto.TipoComodidadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class TipoComodidadeService {

    private TipoComodidadeRepository repository;

    public TipoComodidadeService(TipoComodidadeRepository repository) {
        this.repository = repository;
    }

    public List<TipoComodidade> getTipoComodidades() {
        return repository.findAll();
    }

    public Optional<TipoComodidade> getTipoComodidadeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoComodidade salvar(TipoComodidade tipoComodidade) {
        validar(tipoComodidade);
        return repository.save(tipoComodidade);
    }

    @Transactional
    public void excluir(TipoComodidade tipoComodidade) {
        Objects.requireNonNull(tipoComodidade.getId());
        repository.delete(tipoComodidade);
    }

    public void validar(TipoComodidade tipoComodidade) {
        if (tipoComodidade.getDescricao() == null || tipoComodidade.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (tipoComodidade.getTitulo() == null || tipoComodidade.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        List<TipoComodidade> tipoComodidades = getTipoComodidades();
        if(tipoComodidades.stream().anyMatch((x) -> {return x.getTitulo().trim().equals(tipoComodidade.getTitulo().trim())&&!tipoComodidade.getId().equals(x.getId());})) {
            throw new RegraNegocioException("Título já cadastrado");
        }
    }
}