package com.example.SCHapi.api.dto.Servico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTOList;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Long status;
    private Float valorPorHorario;
    private String tipoReserva;
    private Long idHotel;
    private Long idTipoServico;
    private Long idStatusServico;
    private List<HorarioServicoDTO> horarioServicos;

    public static ServicoDTO create(Servico servico) {
        // ModelMapper modelMapper = new ModelMapper();
        // ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);

        ServicoDTO dto = new ServicoDTO();
        dto.id = servico.getId();
        dto.titulo = servico.getTitulo();
        dto.descricao = servico.getDescricao();
        dto.status = servico.getStatusServico().getId();
        dto.valorPorHorario = servico.getValorPorHorario();
        dto.tipoReserva = servico.getTipoReserva();
        dto.idHotel = servico.getHotel().getId();
        dto.idTipoServico = servico.getTipoServico().getId();
        dto.idStatusServico = servico.getStatusServico().getId();
        dto.horarioServicos = HorarioServicoDTO.createList(servico.getHorarioServicos());

        // dto.status = servico.getStatusServico().getId();
        // dto.status = dto.getId();
        // dto.idHotel = servico.getHotel().getId();
        // dto.idTipoServico = servico.getTipoServico().getId();
        // dto.idStatusServico = servico.getStatusServico().getId();
        return dto;
    }
}
