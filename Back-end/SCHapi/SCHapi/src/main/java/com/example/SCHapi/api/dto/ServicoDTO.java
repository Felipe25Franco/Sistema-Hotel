package com.example.SCHapi.api.dto;
import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.HorarioServico;

import com.example.SCHapi.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Float valorhorario;
    private String status;
    private String tipoReserva;
    private Long idHotel;
    private Long idTipoServico;
    private HorarioServicoDTO horarioServico;

    public static ServicoDTO create(ServicoController servico) {
        ModelMapper modelMapper = new ModelMapper();
        ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);

        dto.idHotel = servico.getHotel().getId();
        dto.idTipoServico = servico.getTipoServico().getId();
        return dto;
    }
}
