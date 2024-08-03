package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.repository.Pessoa.ClienteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getClientes() {
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente) {

        String telefone1 = cliente.getTelefone1();
        String telefone2 = cliente.getTelefone2();
        String cpf = cliente.getCpf();
        Integer numero = cliente.getEndereco().getNumero();
        String cep = cliente.getEndereco().getCep();

        if (cliente.getNome() == null || cliente.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome Invalido!!! Insira um nome valido.");
        }
        if (cpf != null) {
            cpf = cpf.replaceAll("[.\\-]", "");
            if (cpf.length() != 11) {
                throw new RegraNegocioException("CPF Invalido!!! Insira um CPF valido, com 11 digitos.");
            }
        }
        if (telefone1 != null) {
            telefone1 = telefone1.replaceAll("[()\\-]", ""); // Remove parênteses e traços
            if (telefone1.length() != 12 && telefone1.length() != 13) {
                throw new RegraNegocioException("O telefone 1 não pode estar nulo e deve ter 12 ou 13 dígitos.");
            }
        }
        if (telefone2 != null) {
            telefone2 = telefone2.replaceAll("[()\\-]", ""); // Remove parênteses e traços
            if (telefone2.length() != 12 && telefone2.length() != 13) {
                throw new RegraNegocioException("O telefone 2 não pode estar nulo e deve ter 12 ou 13 dígitos.");
            }
        }
        if (cliente.getSenha() == null || cliente.getSenha().trim().equals("") || !cliente.getSenha().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")){
            throw new RegraNegocioException("Senha invalida, a senha deve conter no minimo uma letra maiuscula, no minimo um numero, no minimo um caracter especial e ter no minimo 8 digitos!!!!");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().equals("") || !cliente.getEmail().contains("@")) {
            throw new RegraNegocioException("O e-mail deve conter um '@'.");
        }
        if (cliente.getDataNascimento() == null ||
                !cliente.getDataNascimento().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            throw new RegraNegocioException("Data de nascimento inválida! Insira uma data de nascimento no formato dd/MM/yyyy.");
        }
        if (cliente.getDescricao() == null || cliente.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Descrição Invalido!!! Insira uma descrição valida.");
        }
        if (cliente.getEndereco().getLogradouro() == null || cliente.getEndereco().getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro Invalido!!! Insira um Logradouro valido.");
        }
        if (cliente.getEndereco().getNumero() <= 0 || numero == null ) {
            throw new RegraNegocioException("Numero Invalido!!! O numero de endereço tem que ser maior que 0 e não pode ser nulo.");
        }
        if (cliente.getEndereco().getBairro() == null || cliente.getEndereco().getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro Invalido!!! Insira um Bairro valido.");
        }
        if (cliente.getEndereco().getCidade() == null || cliente.getEndereco().getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade Invalida!!! Insira uma Cidade valida.");
        }
        if (cliente.getEndereco().getCep() == null || cliente.getEndereco().getCep().trim().equals("")){
            throw new RegraNegocioException("CEP InvalidO!!! Insira uma CEP valido.");
        }
        cep = cep.replaceAll("\\s|-", "");       
        if (cep.length() != 8) {
            throw new RegraNegocioException("CEP Inválido!!! O CEP deve ter exatamente 8 dígitos.");
        }
        if (cliente.getEndereco().getUf() == null || cliente.getEndereco().getUf().getId() == null || cliente.getEndereco().getUf().getId() == 0) {
            throw new RegraNegocioException("Uf inválido!!!!");
        }
        if (cliente.getEndereco().getUf().getPais() == null || cliente.getEndereco().getUf().getPais().getId() == null || cliente.getEndereco().getUf().getPais().getId() == 0) {
            throw new RegraNegocioException("Pais inválido!!!!");
        }

        
    }

}