package com.example.SCHapi.service;



import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.TipoProduto;
import com.example.SCHapi.model.entity.TipoServico;

import com.example.SCHapi.model.repository.TipoServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicoService {
    private TipoServicoRepository repository;

    public TipoServicoService(TipoServicoRepository repository) {
        this.repository = repository;
    }

    public List<TipoServico> getTipoServicos(){
        return repository.findAll();
    }

    public Optional<TipoServico> getTipoServicoById(Long id) {
        return repository.findById(id);
    }



    @Transactional
    public TipoServico salvar(TipoServico tipoServico) {
        validar(tipoServico);
        return repository.save(tipoServico);
    }

    public void validar(TipoServico tipoServico) {
        if (tipoServico.getDescricao() == null || tipoServico.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (tipoServico.getTitulo() == null || tipoServico.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo Invalido!!! Insira uma titulo valido.");
        }
    }
}
