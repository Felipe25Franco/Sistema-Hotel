package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargoDTO {
    private Long id;
    private String cargo;
    private String descricao;
    private Float salarioBase;
    private Long hotelId;

    public static CargoDTO create(Cargo cargo) {
        ModelMapper modelMapper = new ModelMapper();
        CargoDTO dto = modelMapper.map(cargo, CargoDTO.class);

        dto.hotelId = cargo.getHotel().getId();
        return dto;
    }
}
