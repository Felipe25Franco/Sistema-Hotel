package com.example.SCHapi.api.dto.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Produto.Produto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    private String titulo;
    private String descricao;
    private Float preco;
    private Integer quantidadeestoque;
    private Long idHotel;
    private Long idTipoProduto;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);

        dto.preco = produto.getPrecoBase();
        dto.quantidadeestoque = produto.getQuantidadeEstoque();
        // dto.idHotel = produto.getHotel().getId();
        // dto.categoriaProdutoId = produto.getTipoProduto().getId();
        return dto;
    }
}

// {
        
//     "titulo": "misto quente",
//     "descricao": "pao, presunto, queijo",
//     "preco": 5.50,
//     "quantidadeestoque": 100,
//     "idHotel": 1,
//     "idTipoProduto": 1
// }