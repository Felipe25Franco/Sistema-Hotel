package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Comodidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComodidadeDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoriaTipoComodidade;

    public static ComodidadeDTO create(Comodidade comodidade) {
        ModelMapper modelMapper = new ModelMapper();
        ComodidadeDTO dto = modelMapper.map(comodidade, ComodidadeDTO.class);

        dto.categoriaTipoComodidade = comodidade.getTipoComodidade().getCategoria();
        return dto;
    }
}
