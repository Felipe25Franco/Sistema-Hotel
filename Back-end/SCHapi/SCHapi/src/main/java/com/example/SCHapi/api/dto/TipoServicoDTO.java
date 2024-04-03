package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.TipoServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoServicoDTO {
    
    private Long id; 
    private String titulo;
    private String descricao;
    
    public static TipoServicoDTO create(TipoServico tipoServico) {
        ModelMapper modelMapper = new ModelMapper();
        TipoServicoDTO dto = modelMapper.map(tipoServico, TipoServicoDTO.class);

        return dto;
    }
}
