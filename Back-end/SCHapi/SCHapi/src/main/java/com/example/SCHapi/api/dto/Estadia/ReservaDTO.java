package com.example.SCHapi.api.dto.Estadia;

import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTOList;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.repository.Quarto.TipoQuartoRepository;
import com.example.SCHapi.service.Estadia.ReservaService;
import com.example.SCHapi.service.Estadia.Lista.TipoQuartoReservaService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReservaDTO {

    private Long id;
    private Long status;
    private String dataInicio;
    private String dataFim;
    private Float valorReserva;
    private Long idCliente;
    private Long idFuncionario;
    private Long idHotel;
    private Long idStatusReserva;
    private List<TipoQuartoReservaDTOList> listaQuartos;

    public static ReservaDTO create(Reserva reserva) {
        // ModelMapper modelMapper = new ModelMapper();
        // ReservaDTO2 dto = modelMapper.map(reserva, ReservaDTO2.class);

        ReservaDTO dto = new ReservaDTO();
        dto.id = reserva.getId();
        dto.status = reserva.getStatusReserva().getId();
        dto.dataInicio = reserva.getDataInicio();
        dto.dataFim = reserva.getDataFim();
        dto.valorReserva = reserva.getValorReserva();
        dto.idCliente = reserva.getCliente().getId();
        if(reserva.getFuncionario()!=null)
            dto.idFuncionario = reserva.getFuncionario().getId();
        else
            dto.idFuncionario = null;
        dto.idHotel = reserva.getHotel().getId();
        dto.idStatusReserva = reserva.getStatusReserva().getId();

        dto.listaQuartos = TipoQuartoReservaDTOList.createList(reserva.getTipoQuartoReserva());

        return dto;

        // dto.status = reserva.getStatusReserva().getId();
        // dto.status = dto.idStatusReserva;        
        
        //aqui tem q pegar o TipoQuartoReserva e criar o ListaQuartos
       // TipoQuartoReservaService tipoQuartoReservaService = new TipoQuartoReservaService(factory.getRepository(TipoQuartoRepository.class);
        // ReservaService reservaService = new ReservaService(null);
        // Optional<Reserva> reservaO = reservaService.getReservaById(dto.getId());
        //dto.listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(Optional.of(reserva));
        // for (TipoQuartoReserva tipoQuartoReserva : listaQuartos) {
        //     dto.listaQuartos.add
        // }
        // dto.idHotel = reserva.getHotel().getId();
        // dto.idCliente = reserva.getCliente().getId();
        // dto.idFuncionario = reserva.getFuncionario().getId();
        // dto.idTipoQuarto = reserva.getTipoQuarto().getId();
        // dto.idStatusReserva = reserva.getStatusReserva().getId();
    }

    // -------------------antigo antes do att inverso

    // public static ReservaDTO create(Reserva reserva, List<TipoQuartoReserva> listaQuartos) {
    //     // ModelMapper modelMapper = new ModelMapper();
    //     // ReservaDTO2 dto = modelMapper.map(reserva, ReservaDTO2.class);

    //     ReservaDTO dto = new ReservaDTO();
    //     dto.id = reserva.getId();
    //     dto.status = reserva.getStatusReserva().getId();
    //     dto.dataInicio = reserva.getDataInicio();
    //     dto.dataFim = reserva.getDataFim();
    //     dto.valorReserva = reserva.getValorReserva();
    //     dto.idCliente = reserva.getCliente().getId();
    //     if(reserva.getFuncionario()!=null)
    //         dto.idFuncionario = reserva.getFuncionario().getId();
    //     else
    //         dto.idFuncionario = null;
    //     dto.idHotel = reserva.getHotel().getId();
    //     dto.idStatusReserva = reserva.getStatusReserva().getId();

    //     dto.listaQuartos = TipoQuartoReservaDTOList.createList(listaQuartos);

    //     return dto;

    //     // dto.status = reserva.getStatusReserva().getId();
    //     // dto.status = dto.idStatusReserva;        
        
    //     //aqui tem q pegar o TipoQuartoReserva e criar o ListaQuartos
    //    // TipoQuartoReservaService tipoQuartoReservaService = new TipoQuartoReservaService(factory.getRepository(TipoQuartoRepository.class);
    //     // ReservaService reservaService = new ReservaService(null);
    //     // Optional<Reserva> reservaO = reservaService.getReservaById(dto.getId());
    //     //dto.listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(Optional.of(reserva));
    //     // for (TipoQuartoReserva tipoQuartoReserva : listaQuartos) {
    //     //     dto.listaQuartos.add
    //     // }
    //     // dto.idHotel = reserva.getHotel().getId();
    //     // dto.idCliente = reserva.getCliente().getId();
    //     // dto.idFuncionario = reserva.getFuncionario().getId();
    //     // dto.idTipoQuarto = reserva.getTipoQuarto().getId();
    //     // dto.idStatusReserva = reserva.getStatusReserva().getId();
    // }
}
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// class TipoQuartoReservaDTO {
//     private Long id;
//     private Long tipoQuarto;
//     private Integer qtd;

//     public static TipoQuartoReservaDTO create(TipoQuartoReserva tipoQuartoReserva) {
//         TipoQuartoReservaDTO dto = new TipoQuartoReservaDTO();
//         dto.setId(tipoQuartoReserva.getId());
//         dto.setTipoQuarto(tipoQuartoReserva.getTipoQuarto().getId());
//         dto.setQtd(tipoQuartoReserva.getQtd());
//         return dto;
//     }

//     public static List<TipoQuartoReservaDTO> createList (List<TipoQuartoReserva> listaQuartos) {
//         List<TipoQuartoReservaDTO> listDto = new ArrayList<TipoQuartoReservaDTO>();
//         for (TipoQuartoReserva tipoQuartoReserva : listaQuartos) {
//             listDto.add(TipoQuartoReservaDTO.create(tipoQuartoReserva));
//         }
//         return listDto;
//     }
// }