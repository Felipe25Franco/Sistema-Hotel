package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.example.SCHapi.model.repository.Estadia.Lista.ProdutoSolicitadoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ProdutoSolicitadoService {

    private ProdutoSolicitadoRepository repository;

    public ProdutoSolicitadoService(ProdutoSolicitadoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoSolicitado> getProdutoSolicitados() {
        return repository.findAll();
    }

    public Optional<ProdutoSolicitado> getProdutoSolicitadoById(Long id) {
        return repository.findById(id);
    }

    public List<ProdutoSolicitado> getProdutoSolicitadoByHospedagem(Optional<Hospedagem> hospedagem) {
        return repository.findByHospedagem(hospedagem);
    }

    @Transactional
    public ProdutoSolicitado salvar(ProdutoSolicitado produtoSolicitado) {
        // validar(produtoSolicitado);
        return repository.save(produtoSolicitado);
    }

    @Transactional
    public void excluir(ProdutoSolicitado produtoSolicitado) {
        Objects.requireNonNull(produtoSolicitado.getId());
        repository.delete(produtoSolicitado);
    }
}