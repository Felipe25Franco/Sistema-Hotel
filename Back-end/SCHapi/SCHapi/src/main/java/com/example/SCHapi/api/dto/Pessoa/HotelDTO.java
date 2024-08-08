

package com.example.SCHapi.api.dto.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Hotel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Float avaliacaoMedia;
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

    public static HotelDTO create(Hotel hotel) {
        //ModelMapper modelMapper = new ModelMapper();
        //HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);
        HotelDTO dto = new HotelDTO();
        
        dto.id = hotel.getId();
        dto.descricao = hotel.getDescricao();
        dto.titulo = hotel.getTitulo();
        dto.id = hotel.getId();
        // dto.idUf = hotel.getUf().getId();
        // dto.idPais = hotel.getPais().getId();

        //dps arrumor aq pra dividir.
        dto.avaliacaoMedia = hotel.getMediaAvaliacao();
        dto.ddi1 = hotel.getTelefone1().substring(0,2);
        dto.ddd1 = hotel.getTelefone1().substring(2,4);
        dto.num1 = hotel.getTelefone1().substring(4);
        dto.ddi2 = hotel.getTelefone2().substring(0,2);
        dto.ddd2 = hotel.getTelefone2().substring(2,4);
        dto.num2 = hotel.getTelefone2().substring(4);

        dto.numero = hotel.getEndereco().getNumero();
        dto.complemento = hotel.getEndereco().getComplemento();
        dto.logradouro = hotel.getEndereco().getLogradouro();
        dto.bairro = hotel.getEndereco().getBairro();
        dto.cep = hotel.getEndereco().getCep();
        dto.cidade = hotel.getEndereco().getCidade();

        dto.idUf = hotel.getEndereco().getUf().getId();
        dto.idPais = hotel.getEndereco().getUf().getPais().getId();

        return dto;
    }
}