package com.example.SCHapi.api.dto;


import com.example.SCHapi.model.entity.Endereco;
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
    private String uf;
    private String pais;

    public static EnderecoDTO create(Endereco endereco) {
        ModelMapper modelMapper = new ModelMapper();
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);

        dto.uf = endereco.getUf().getTitulo();
        dto.pais = endereco.getUf().getPais().getTitulo();
        return dto;
    }
}
