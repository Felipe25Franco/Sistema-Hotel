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
    private Long idRow;
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

    public static List<ProdutoSolicitadoDTOList> createList(List<ProdutoSolicitado> list) {
        List<ProdutoSolicitadoDTOList> listDto = new ArrayList<>();
        Long idRow = 0L; // Esse ID é para padronizar o ID da tabela no front, pois o ID original não importa

        for (ProdutoSolicitado produtoSolicitado : list) {
            ProdutoSolicitadoDTOList dto = ProdutoSolicitadoDTOList.create(produtoSolicitado);
            listDto.add(dto);
            idRow++;
            // Atualiza o ID da linha no último elemento da lista
            if (!listDto.isEmpty()) {
                listDto.get(listDto.size() - 1).setIdRow(idRow);
            }
        }

        return listDto;
    }
}
