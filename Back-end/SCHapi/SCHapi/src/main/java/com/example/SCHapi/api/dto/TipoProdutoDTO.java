package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoProdutoDTO {

    private Long id;
    private String descricao;
    private String tipo;

    public static TipoProdutoDTO create(TipoProduto tipoProduto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoProdutoDTO dto = modelMapper.map(tipoProduto, TipoProdutoDTO.class);

        return dto;
    }
}
