package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.CamaTipoQuarto;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.TipoCama;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCamaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer ocupantes;

    public static TipoCamaDTO create(TipoCama tipoCama) {
        ModelMapper modelMapper = new ModelMapper();
        TipoCamaDTO dto = modelMapper.map(tipoCama, TipoCamaDTO.class);

        return dto;
    }
}
