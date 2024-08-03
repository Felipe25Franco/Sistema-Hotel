package com.example.SCHapi.api.dto.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Funcionario;

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
    private Long idHotel;
    private Long idCargo;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);

        // dto.idCargo = funcionario.getCargo().getId();
        // dto.idHotel = funcionario.getHotel().getId();
        // dto.idUf = funcionario.getUf().getId();
        // dto.idPais = funcionario.getPais().getId();

        //dps arrumor aq pra dividir.
        dto.ddi1 = funcionario.getTelefone1().substring(0,2);
        dto.ddd1 = funcionario.getTelefone1().substring(2,4);
        dto.num1 = funcionario.getTelefone1().substring(4);
        dto.ddi2 = funcionario.getTelefone2().substring(0,2);
        dto.ddd2 = funcionario.getTelefone2().substring(2,4);
        dto.num2 = funcionario.getTelefone2().substring(4);

        dto.numero = funcionario.getEndereco().getNumero();
        dto.complemento = funcionario.getEndereco().getComplemento();
        dto.logradouro = funcionario.getEndereco().getLogradouro();
        dto.bairro = funcionario.getEndereco().getBairro();
        dto.cep = funcionario.getEndereco().getCep();
        dto.cidade = funcionario.getEndereco().getCidade();

        dto.idUf = funcionario.getEndereco().getUf().getId();
        dto.idPais = funcionario.getEndereco().getUf().getPais().getId();


        return dto;
    }
}
