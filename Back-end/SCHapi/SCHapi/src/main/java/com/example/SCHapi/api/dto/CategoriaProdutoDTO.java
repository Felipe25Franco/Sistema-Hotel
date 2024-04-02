package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.CategoriaProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaProdutoDTO {

    private Long id;
    private String descricao;
    private String categoria;

    public static CategoriaProdutoDTO create(CategoriaProduto categoriaProduto) {
        ModelMapper modelMapper = new ModelMapper();
        CategoriaProdutoDTO dto = modelMapper.map(categoriaProduto, CategoriaProdutoDTO.class);

        return dto;
    }
}
