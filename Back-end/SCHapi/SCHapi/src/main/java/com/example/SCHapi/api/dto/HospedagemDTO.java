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

    private String nomeCliente;
    private String nomeFuncionario;
    private String tituloHotel;
    private String tituloStatusHospedagem;
    //private String tituloTipoQuarto;
    private float notaAvaliacaoHospedagem;

    private String tituloReserva;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);

        dto.tituloHotel = hospedagem.getHotel().getTitulo();
        dto.nomeCliente = hospedagem.getCliente().getNome();
        dto.nomeFuncionario = hospedagem.getFuncionario().getNome();
        dto.tituloStatusHospedagem = hospedagem.getStatusHospedagem().getTitulo();
        // dto.idTipoQuarto = hospedagem.getTipoQuarto().getId();
        dto.tituloReserva = hospedagem.getReserva().getStatusReserva().getTitulo();
        dto.notaAvaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem().getId();
        return dto;
    }
}
