package com.example.SCHapi.api.dto.Estadia.Lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoSolicitadoDTO {

    private Long id;
    private Long idProduto;
    private Long idHospedagem;

    public static ProdutoSolicitadoDTO create(ProdutoSolicitado produtoSolicitado) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoSolicitadoDTO dto = modelMapper.map(produtoSolicitado, ProdutoSolicitadoDTO.class);

        return dto;
    }
}
