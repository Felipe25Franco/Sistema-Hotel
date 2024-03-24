package com.example.SCHapi.api.dto;

import java.util.Date;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
    private Integer ddi1;
    private Integer ddd1;
    private Integer num1;
    private Integer ddi2;
    private Integer ddd2;
    private Integer num2;
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



        return dto;
    }
}
