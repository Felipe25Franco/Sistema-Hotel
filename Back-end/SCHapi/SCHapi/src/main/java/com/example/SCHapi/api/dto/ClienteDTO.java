package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
    private String telefone1;
    private String telefone2;
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



        dto.numero = cliente.getEndereco().getNumero();
        dto.complemento = cliente.getEndereco().getComplemento();
        dto.logradouro = cliente.getEndereco().getLogradouro();
        dto.bairro = cliente.getEndereco().getBairro();
        dto.cep = cliente.getEndereco().getCep();
        dto.cidade = cliente.getEndereco().getCidade();
        return dto;
    }
}
