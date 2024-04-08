package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String senha;
    private Float salario;
    private String horaInicio;
    private String horaFim;
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
    private Long idHotel;
    private Long idCargo;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);

        dto.numero = funcionario.getEndereco().getNumero();
        dto.logradouro = funcionario.getEndereco().getLogradouro();
        dto.complemento = funcionario.getEndereco().getComplemento();
        dto.bairro = funcionario.getEndereco().getBairro();
        dto.cidade = funcionario.getEndereco().getCidade();
        dto.cep = funcionario.getEndereco().getCep();


        return dto;
    }
}
