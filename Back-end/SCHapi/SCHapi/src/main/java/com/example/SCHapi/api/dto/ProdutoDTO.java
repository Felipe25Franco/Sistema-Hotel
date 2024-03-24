package com.example.SCHapi.api.dto;
import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Float preco;
    private Integer quantidadeestoque;
    private Long idHotel;
    private Long idTipoProduto;

    public static ProdutoDTO create(ProdutoController produto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);

        dto.idHotel = produto.getHotel().getId();
        return dto;
    }
}
