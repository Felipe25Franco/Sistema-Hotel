package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.ProdutoSolicitado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoSolicitadoDTO {
    private Long id;
    private Integer quant;
    private Float valorTotal;
    private Long idProduto;

    public static ProdutoSolicitadoDTO create(ProdutoSolicitado produtoSolicitado) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoSolicitadoDTO dto = modelMapper.map(produtoSolicitado, ProdutoSolicitadoDTO.class);

        dto.idProduto = produtoSolicitado.getProduto().getId();
        return dto;
    }
}
