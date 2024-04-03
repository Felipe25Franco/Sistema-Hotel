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
    private Date dataNacimento;
    private String email;
    private String senha;
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
        dto.ddi1 = cliente.getTelefone1();
        dto.ddd1 = cliente.getTelefone1();
        dto.num1 = cliente.getTelefone1();
        dto.ddi2 = cliente.getTelefone2();
        dto.ddd2 = cliente.getTelefone2();
        dto.num2 = cliente.getTelefone2();

        dto.numero = cliente.getEndereco().getNumero();
        dto.complemento = cliente.getEndereco().getComplemento();
        dto.logradouro = cliente.getEndereco().getLogradouro();
        dto.bairro = cliente.getEndereco().getBairro();
        dto.cep = cliente.getEndereco().getCep();
        dto.cidade = cliente.getEndereco().getCidade();
        return dto;
    }
}
