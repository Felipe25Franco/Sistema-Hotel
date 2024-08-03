package com.example.SCHapi.api.dto.Pessoa;

import com.example.SCHapi.model.entity.Pessoa.Uf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UfDTO {
    private Long id;
    private String titulo;
    private Long idPais;

    public static UfDTO create(Uf uf) {
        ModelMapper modelMapper = new ModelMapper();
        UfDTO dto = modelMapper.map(uf, UfDTO.class);

        return dto;
    }
}
