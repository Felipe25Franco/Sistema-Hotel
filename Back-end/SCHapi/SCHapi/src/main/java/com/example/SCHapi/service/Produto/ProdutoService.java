package com.example.SCHapi.service.Produto;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.repository.Produto.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ProdutoService {

    private ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> getProdutos() {
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto) {
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {

        Integer qntEstoque = produto.getQuantidadeEstoque();
        Float preco = produto.getPrecoBase();

        if (produto.getTitulo() == null || produto.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (produto.getQuantidadeEstoque() <= 0 || qntEstoque == null) {
            throw new RegraNegocioException("A quantidade em estoque de um produto tem que ser maior que 0.");
        }
        if (produto.getPrecoBase() <= 0 || preco == null) {
            throw new RegraNegocioException("O preço de um produto tem que ser maior que 0.");
        }
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getId() == null || produto.getTipoProduto().getId() == 0) {
            throw new RegraNegocioException("Tipo de produto inválido!!!!");
        }
        if (produto.getHotel() == null || produto.getHotel().getId() == null || produto.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválido!!!!");
        }
    }

}