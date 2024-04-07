package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    private String titulo;
    private String descricao;
    private double precoBase;
    private int quantidadeEstoque;
    private String nomeHotel;
    private String categoriaTipoProduto;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);

        dto.nomeHotel = produto.getHotel().getTitulo();
        dto.categoriaTipoProduto = produto.getTipoProduto().getCategoria();
        return dto;
    }
}
