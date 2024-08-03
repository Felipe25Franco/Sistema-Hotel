package com.example.SCHapi.api.dto.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Cargo;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargoDTO {
    private Long id;
    private String cargo;
    private String descricao;
    private Float salarioBase;
    private Long idHotel;

    public static CargoDTO create(Cargo cargo) {
        ModelMapper modelMapper = new ModelMapper();
        CargoDTO dto = modelMapper.map(cargo, CargoDTO.class);

        //dto.idHotel = cargo.getHotel().getId();
        return dto;
    }
}
