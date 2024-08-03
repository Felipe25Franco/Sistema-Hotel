package com.example.SCHapi.api.dto.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Produto.TipoProduto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoProdutoDTO {

    private Long id;
    private String titulo;
    private String descricao;

    public static TipoProdutoDTO create(TipoProduto tipoProduto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoProdutoDTO dto = modelMapper.map(tipoProduto, TipoProdutoDTO.class);

        return dto;
    }
}
