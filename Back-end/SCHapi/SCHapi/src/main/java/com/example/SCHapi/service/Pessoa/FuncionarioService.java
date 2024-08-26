package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.repository.Pessoa.FuncionarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {

        String telefone1 = funcionario.getTelefone1();
        String telefone2 = funcionario.getTelefone2();
        String cpf = funcionario.getCpf();
        Integer numero = funcionario.getEndereco().getNumero();
        String cep = funcionario.getEndereco().getCep();

        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")){
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
        // if (funcionario.getSenha() == null || funcionario.getSenha().trim().equals("") || !funcionario.getSenha().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")){
        //     throw new RegraNegocioException("Senha invalida, a senha deve conter no minimo uma letra maiuscula, no minimo um numero, no minimo um caracter especial e ter no minimo 8 digitos!!!!");
        // }
        if (funcionario.getEmail() == null || funcionario.getEmail().trim().equals("") || !funcionario.getEmail().contains("@")) {
            throw new RegraNegocioException("O e-mail deve conter um '@'.");
        }
        if (funcionario.getDataNascimento() == null || !funcionario.getDataNascimento().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new RegraNegocioException("Data de nascimento inválida! Insira uma data de nascimento no formato dd/MM/yyyy.");
        }
        // if (funcionario.getSalario() < 1412.00 || funcionario.getSalario() == null) {
        //     throw new RegraNegocioException("Funcionario não pode ter um salario abaixo do salario minimo de 1412,00 reais.");
        // }
        if (funcionario.getHoraInicio() == null || funcionario.getHoraInicio().trim().equals("") || !funcionario.getHoraInicio().contains(":") || !funcionario.getHoraInicio().matches("\\d{2}:\\d{2}$")) {
            throw new RegraNegocioException("Horario de entrada invalido   .");
        }
        if (funcionario.getHoraFim() == null || funcionario.getHoraFim().trim().equals("") || !funcionario.getHoraFim().contains(":") || !funcionario.getHoraFim().matches("\\d{2}:\\d{2}$")) {
            throw new RegraNegocioException("Horario de saida invalido   .");
        }
             
        if (funcionario.getCargo() == null || funcionario.getCargo().getId() == null || funcionario.getCargo().getId() == 0) {
            throw new RegraNegocioException("Cargo inválido!!!!");
        }
        System.out.println(funcionario.getHotel().getId());
        if (funcionario.getHotel() == null || funcionario.getHotel().getId() == null || funcionario.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválido!!!!");
        }
        if (funcionario.getEndereco().getLogradouro() == null || funcionario.getEndereco().getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro Invalido!!! Insira um Logradouro valido.");
        }
        if (funcionario.getEndereco().getNumero() <= 0 || numero == null ) {
            throw new RegraNegocioException("Numero Invalido!!! O numero de endereço tem que ser maior que 0 e não pode ser nulo.");
        }
        if (funcionario.getEndereco().getBairro() == null || funcionario.getEndereco().getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro Invalido!!! Insira um Bairro valido.");
        }
        // if (funcionario.getEndereco().getCidade() == null || funcionario.getEndereco().getCidade().trim().equals("")){// || funcionario.getEndereco().getCidade() != funcionario.getHotel().getEndereco().getCidade()
        //     throw new RegraNegocioException("Cidade Invalida!!! Insira uma Cidade valida. O Funcionario tem que residir na mesma cidade onde o Hotel fica!");
        // }
        if (funcionario.getEndereco().getCep() == null || funcionario.getEndereco().getCep().trim().equals("")){
            throw new RegraNegocioException("CEP InvalidO!!! Insira uma CEP valido.");
        }
        cep = cep.replaceAll("\\s|-", "");       
        if (cep.length() != 8) {
            throw new RegraNegocioException("CEP Inválido!!! O CEP deve ter exatamente 8 dígitos.");
        }
        // if (funcionario.getEndereco().getUf() == null || funcionario.getEndereco().getUf().getId() == null || funcionario.getEndereco().getUf().getId() == 0 || funcionario.getEndereco().getUf() != funcionario.getHotel().getEndereco().getUf()) {
        //     throw new RegraNegocioException("Uf inválido!!!! Uf não existe ou O Funcionario tem que residir na mesma UF onde o Hotel fica!");
        // }
        if (funcionario.getEndereco().getUf().getPais() == null || funcionario.getEndereco().getUf().getPais().getId() == null || funcionario.getEndereco().getUf().getPais().getId() == 0) {
            throw new RegraNegocioException("Pais inválido!!!!");
        }

        // DESCRIÇÃO NÃO LANÇADA POIS O ATRIBUTO DESCRIÇÃO FOI CRIADO APENAS PARA GERAR A CLASSE

        List<Funcionario> funcionarios = getFuncionarios();
        if(funcionarios.stream().anyMatch((x) -> {return !funcionario.getId().equals(x.getId())&&(x.getCpf().trim().equals(funcionario.getCpf().trim())||x.getEmail().trim().equals(funcionario.getEmail().trim()));})) {
            throw new RegraNegocioException("CPF ou e-mail já cadastrado");
        }
    }
}