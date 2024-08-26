package com.example.SCHapi.service.Estadia.Lista;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
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
        validar(produtoSolicitado);
        return repository.save(produtoSolicitado);
    }

    @Transactional
    public void excluir(ProdutoSolicitado produtoSolicitado) {
        Objects.requireNonNull(produtoSolicitado.getId());
        repository.delete(produtoSolicitado);
    }

    
    public void validar(ProdutoSolicitado produtoSolicitado) {
        
        if (produtoSolicitado.getProduto() == null || produtoSolicitado.getProduto().getId() == null || produtoSolicitado.getProduto().getId() == 0) {
            throw new RegraNegocioException("Produto inválid0!!!!");
        }
        if (produtoSolicitado.getHospedagem() == null || produtoSolicitado.getHospedagem().getId() == null || produtoSolicitado.getHospedagem().getId() == 0) {
            throw new RegraNegocioException("Hospedagem inválid0!!!!");
        }
        if (produtoSolicitado.getQuantidade()<=0) {
            throw new RegraNegocioException("Quantidade deve ser maior que zero");
        }

        // FALTA LISTA DE QUARTOS
        List<ProdutoSolicitado> produtoSolicitados = getProdutoSolicitadoByHospedagem(Optional.of(produtoSolicitado.getHospedagem()));
        if(produtoSolicitados.stream().anyMatch((x) -> x.getProduto().getId()==produtoSolicitado.getProduto().getId())) {
            throw new RegraNegocioException("Horários duplicado. Selecione Horários distintos");
        }
    }
}