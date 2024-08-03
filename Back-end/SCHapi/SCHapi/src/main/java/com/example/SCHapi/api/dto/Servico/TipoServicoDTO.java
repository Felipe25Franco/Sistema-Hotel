package com.example.SCHapi.api.dto.Servico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Servico.TipoServico;

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
