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
    private Float valorTotalPago;

    private Long idAvaliacaoHospedagem;
    private Long idCliente;
    private Long idFuncionario;
    private Long idHotel;
    private Long idReserva;
    private Long idStatusHospedagem;
    private Long idQuartoHospedagem;




    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);
        return dto;
    }
}
