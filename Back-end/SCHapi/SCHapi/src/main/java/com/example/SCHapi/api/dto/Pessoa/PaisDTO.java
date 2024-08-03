package com.example.SCHapi.api.dto.Pessoa;

import com.example.SCHapi.model.entity.Pessoa.Pais;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisDTO {

    private Long id;
    private String titulo;

    public static PaisDTO create(Pais pais) {
        ModelMapper modelMapper = new ModelMapper();
        PaisDTO dto = modelMapper.map(pais, PaisDTO.class);

        return dto;
    }
}
