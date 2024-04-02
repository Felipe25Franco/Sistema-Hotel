package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String valorReserva;
    private Long idCliente;
    private Long idFuncionario;
    private Long idHotel;
    private Long idTipoQuarto;
    private Long idStatusReserva;




    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);

        dto.idHotel = reserva.getHotel().getId();
        dto.idCliente = reserva.getCliente().getId();
        dto.idFuncionario = reserva.getFuncionario().getId();
        dto.idTipoQuarto = reserva.getTipoQuarto().getId();
        dto.idStatusReserva = reserva.getStatusReserva().getId();

        return dto;
    }
}
