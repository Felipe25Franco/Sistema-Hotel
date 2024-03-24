package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.Pais;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaisDTO {
    private Long id;
    private String titulo;

    public static PaisDTO create(Pais pais) {
        ModelMapper modelMapper = new ModelMapper();
        PaisDTO dto = modelMapper.map(pais, PaisDTO.class);

        return dto;
    }
}
