package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private float valorPorHorario;
    private String tipoReserva;
    private Long idHotel;
    private Long idTipoServico;
    private Long idStatusServico;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);

        dto.idHotel = servico.getHotel().getId();
        dto.idTipoServico = servico.getTipoServico().getId();
        dto.idStatusServico = servico.getStatusServico().getId();
        return dto;
    }
}
