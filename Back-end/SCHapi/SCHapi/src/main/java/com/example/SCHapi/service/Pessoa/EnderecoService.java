package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.repository.Pessoa.EnderecoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class EnderecoService {

    private EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> getEnderecos() {
        return repository.findAll();
    }

    public Optional<Endereco> getEnderecoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Endereco salvar(Endereco endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    @Transactional
    public void excluir(Endereco endereco) {
        Objects.requireNonNull(endereco.getId());
        repository.delete(endereco);
    }

    public void validar(Endereco endereco) {
        
        Integer numero = endereco.getNumero();
        String cep = endereco.getCep();

        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro Invalido!!! Insira um Logradouro valido.");
        }
        if (endereco.getNumero() <= 0 || numero == null ) {
            throw new RegraNegocioException("Numero Invalido!!! O numero de endereço tem que ser maior que 0 e não pode ser nulo.");
        }
        if (endereco.getBairro() == null || endereco.getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro Invalido!!! Insira um Bairro valido.");
        }
        if (endereco.getCidade() == null || endereco.getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade Invalida!!! Insira uma Cidade valida.");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().equals("")){
            throw new RegraNegocioException("CEP InvalidO!!! Insira uma CEP valido.");
        }
        cep = cep.replaceAll("\\s|-", "");       
        if (cep.length() != 8) {
            throw new RegraNegocioException("CEP Inválido!!! O CEP deve ter exatamente 8 dígitos.");
        }

        if (endereco.getUf() == null || endereco.getUf().getId() == null || endereco.getUf().getId() == 0) {
            throw new RegraNegocioException("UF inválido!!!!");
        }
        
    }
}