package com.example.SCHapi.api.dto.Quarto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Quarto.Quarto;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {
    private Long id;
    private Integer numero;
    private Integer andar;
    private String bloco;
    private Long status;
    private Long idHotel;
    private Long idTipoQuarto;
    private Long idStatusQuarto;

    public static QuartoDTO create(Quarto quarto) {
        // ModelMapper modelMapper = new ModelMapper();
        // QuartoDTO dto = modelMapper.map(quarto, QuartoDTO.class);

        QuartoDTO dto = new QuartoDTO();
        dto.id = quarto.getId();
        dto.numero = quarto.getNumero();
        dto.andar = quarto.getAndar();
        dto.bloco = quarto.getBloco();
        dto.status = quarto.getStatusQuarto().getId();
        dto.idHotel = quarto.getHotel().getId();
        dto.idTipoQuarto = quarto.getTipoQuarto().getId();
        dto.idStatusQuarto = quarto.getStatusQuarto().getId();

        // dto.idHotel = quarto.getHotel().getId();
        // dto.idTipoQuarto = quarto.getTipoQuarto().getId();
        // dto.idStatusQuarto = quarto.getStatusQuarto().getId();
        return dto;
    }
}
