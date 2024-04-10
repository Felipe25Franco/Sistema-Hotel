package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Float avaliacaoMedia;
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


    public static HotelDTO create(Hotel hotel) {
        ModelMapper modelMapper = new ModelMapper();
        HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);

        dto.numero = hotel.getEndereco().getNumero();
        dto.logradouro = hotel.getEndereco().getLogradouro();
        dto.complemento = hotel.getEndereco().getComplemento();
        dto.bairro = hotel.getEndereco().getBairro();
        dto.cidade = hotel.getEndereco().getCidade();
        dto.cep = hotel.getEndereco().getCep();

        dto.idUf = hotel.getEndereco().getUf().getId();
        dto.idPais = hotel.getEndereco().getUf().getPais().getId();

        return dto;
    }
}
