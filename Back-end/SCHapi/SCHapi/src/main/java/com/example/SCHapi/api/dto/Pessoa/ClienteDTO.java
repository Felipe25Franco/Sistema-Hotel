package com.example.SCHapi.api.dto.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Cliente;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String senha;
    private String descricao;
    //private String telefone1;
    //private String telefone2;
    private String ddi1;
    private String ddd1;
    private String num1;
    private String ddi2;
    private String ddd2;
    private String num2;
    private Integer numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private Long idUf;
    private Long idPais;

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);

        //dps arrumor aq pra dividir.
        dto.ddi1 = cliente.getTelefone1().substring(0,2);
        dto.ddd1 = cliente.getTelefone1().substring(2,4);
        dto.num1 = cliente.getTelefone1().substring(4);
        dto.ddi2 = cliente.getTelefone2().substring(0,2);
        dto.ddd2 = cliente.getTelefone2().substring(2,4);
        dto.num2 = cliente.getTelefone2().substring(4);

        dto.numero = cliente.getEndereco().getNumero();
        dto.complemento = cliente.getEndereco().getComplemento();
        dto.logradouro = cliente.getEndereco().getLogradouro();
        dto.bairro = cliente.getEndereco().getBairro();
        dto.cep = cliente.getEndereco().getCep();
        dto.cidade = cliente.getEndereco().getCidade();

        dto.idUf = cliente.getEndereco().getUf().getId();
        dto.idPais = cliente.getEndereco().getUf().getPais().getId();

        return dto;
    }
}

// {
//     "nome" :"a",
//     "cpf":"11111111111",
//     "dataNascimento":"22/22/2222",
//    "email":"a@",
//     "senha":"a2A@dwqdwqdqwdqwdqwd",
//     "ddi1":"11",
//     "ddd1":"22",
//     "num1":"33330000",
//     "ddi2":"11",
//     "ddd2":"22",
//    "num2":"333330000",
//     "numero":1,
//    "complemento":1,
//     "logradouro":"a",
//     "bairro":"a",
//     "cep":"a",
//    "cidade":"a",
//    "idUf":1,
//    "idPais":1}