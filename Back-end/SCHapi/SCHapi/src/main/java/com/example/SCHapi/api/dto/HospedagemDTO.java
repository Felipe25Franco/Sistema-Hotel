package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Hospedagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospedagemDTO {

    private Long id;
    private Date dataInicio;
    private Date dataFim1;
    private Date dataFim2;
    private Float valorEstadia;
    private String statusValorEstadia;
    private Float valorConsumo;
    private Float valorServicos;
    private Float valorEstadiaAdicional;
    private Float valorTotal;
    private Long idCliente;
    private Long idFuncionario;
    private Long idHotel;
    private Long idStatusHospedagem;
    private Long idTipoQuarto;

    private Long idReserva;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);

        dto.idHotel = hospedagem.getHotel().getId();
        dto.idCliente = hospedagem.getCliente().getId();
        dto.idFuncionario = hospedagem.getFuncionario().getId();
        dto.idStatusHospedagem = hospedagem.getStatusHospedagem().getId();
        dto.idTipoQuarto = hospedagem.getTipoQuarto().getId();
        dto.idReserva = hospedagem.getReserva().getId();
        return dto;
    }
}
