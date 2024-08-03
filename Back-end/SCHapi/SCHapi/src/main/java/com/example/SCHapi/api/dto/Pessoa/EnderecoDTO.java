package com.example.SCHapi.api.dto.Pessoa;

import com.example.SCHapi.model.entity.Pessoa.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id;
    private int numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;
    private Long idUf;

    public static EnderecoDTO create(Endereco endereco) {
        ModelMapper modelMapper = new ModelMapper();
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);

        return dto;
    }
}
