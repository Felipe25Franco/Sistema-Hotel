package com.example.SCHapi.api.dto;
import java.util.Date;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.ProdutoSolicitado;

import com.example.SCHapi.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private String status;
    private Date dataInicio;
    private Date dataFim;
    private String valorReserva;
    private Long idCliente;
    private Long idFuncionario;
    private Long idHotel;
    private Long idTipoQuarto;
    private Long idReserva;

    private ListaQuartosReservaDTO listaQuartos;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);

        dto.idHotel = reserva.getHotel().getId();
        dto.idCliente = reserva.getCliente().getId();
        dto.idReserva = reserva.getFuncionario().getId();
        return dto;
    }
}
