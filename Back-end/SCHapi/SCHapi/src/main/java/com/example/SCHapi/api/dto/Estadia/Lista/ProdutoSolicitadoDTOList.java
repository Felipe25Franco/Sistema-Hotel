package com.example.SCHapi.api.dto.Estadia.Lista;

import java.util.ArrayList;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoSolicitadoDTOList {
    private Long id;
    private Integer quant;
    private Float valorTotal;
    private Long idProduto;

    public static ProdutoSolicitadoDTOList create(ProdutoSolicitado produtoSolicitado) {
        ProdutoSolicitadoDTOList dto = new ProdutoSolicitadoDTOList();
        dto.setId(produtoSolicitado.getId());
        dto.setQuant(produtoSolicitado.getQuantidade());
        dto.setValorTotal(produtoSolicitado.getValorTotal());
        dto.setIdProduto(produtoSolicitado.getProduto().getId());
        return dto;
    }

    public static List<ProdutoSolicitadoDTOList> createList (List<ProdutoSolicitado> list) {
        List<ProdutoSolicitadoDTOList> listDto = new ArrayList<ProdutoSolicitadoDTOList>();
        for (ProdutoSolicitado produtoSolicitado : list) {
            listDto.add(ProdutoSolicitadoDTOList.create(produtoSolicitado));
        }
        return listDto;
    }
}

