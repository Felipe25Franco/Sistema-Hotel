package com.example.SCHapi.api.dto;
import com.example.SCHapi.model.entity.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoProdutoDTO {
    private Long id;
    private String titulo;
    private String descricao;


}
