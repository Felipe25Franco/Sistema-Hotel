package com.example.SCHapi.api.dto.Estadia;

import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.QuartoHospedagemDTOList;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospedagemDTO {

    private Long id;
    private Long status;
    private String dataInicio;
    private String dataFim1;
    private String dataFim2;
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
    // private Long idAvaliacaoHospedagem;
    private Long idReserva;
    
    private List<QuartoHospedagemDTOList> listaQuartos;
    private List<ProdutoSolicitadoDTOList> produtoHospedagem;

    private Float nota;
    private String comentario;

    public HospedagemDTO(Reserva reserva, List<TipoQuartoReserva> listaQuartos) {
        this.id = null;
        this.status = (long) 1;
        this.dataInicio = ""+LocalDate.now().getDayOfMonth()+"/"+LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear(); // mudar pra data do sisteam
        this.dataFim1 = reserva.getDataFim();
        this.dataFim2 = null;
        this.valorEstadia = reserva.getValorReserva();
        if (reserva.getStatusReserva().getId() == 1 || reserva.getStatusReserva().getId() == 3)
            this.statusValorEstadia = "Pago";
        else
            this.statusValorEstadia = "Não pago";
        this.valorConsumo = (float) 0;
        this.valorServicos = (float) 0;
        this.valorEstadiaAdicional = (float) 0;
        this.valorTotal = (float) 0;
        this.idCliente = reserva.getCliente().getId();
        this.idFuncionario = null;
        this.idHotel = reserva.getHotel().getId();
        this.idStatusHospedagem = (long) 1;
        // this.idAvaliacaoHospedagem = null;
        this.idReserva = reserva.getId();
        this.listaQuartos = new ArrayList<QuartoHospedagemDTOList>();
        for (TipoQuartoReserva tipoQuarto: listaQuartos){
            for(int i = 1;i<=tipoQuarto.getQtd();i++){
                this.listaQuartos.add(new QuartoHospedagemDTOList(null, tipoQuarto.getTipoQuarto().getId(), 0, null));
            }
        }
        this.produtoHospedagem = null;
        this.nota = (float) -1;
        this.comentario = null;
    }



    // public static HospedagemDTO create(Hospedagem hospedagem, List<QuartoHospedagem> listaQuartos, List<ProdutoSolicitado> produtoHospedagem) {
    public static HospedagemDTO create(Hospedagem hospedagem) {
        // ModelMapper modelMapper = new ModelMapper();
        // HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);

        
        HospedagemDTO dto = new HospedagemDTO();
        dto.id = hospedagem.getId();
        dto.status = hospedagem.getStatusHospedagem().getId();
        dto.dataInicio = hospedagem.getDataInicio();
        dto.dataFim1 = hospedagem.getDataFim1();
        dto.dataFim2 = hospedagem.getDataFim2();
        dto.valorEstadia = hospedagem.getValorEstadia();
        dto.statusValorEstadia = hospedagem.getStatusValorEstadia();
        dto.valorConsumo = hospedagem.getValorConsumo();
        dto.valorServicos = hospedagem.getValorServicos();
        dto.valorEstadiaAdicional = hospedagem.getValorEstadiaAdicional();
        dto.valorTotal = hospedagem.getValorTotalPago();
        dto.idCliente = hospedagem.getCliente().getId();
        dto.idFuncionario = hospedagem.getFuncionario().getId();
        dto.idHotel = hospedagem.getHotel().getId();
        dto.idStatusHospedagem = hospedagem.getStatusHospedagem().getId();
        if(hospedagem.getAvaliacaoHospedagem()!=null) {
            // dto.idAvaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem().getId();
            dto.nota = hospedagem.getAvaliacaoHospedagem().getNota();
            dto.comentario = hospedagem.getAvaliacaoHospedagem().getComentario();
        }
        else {
            dto.nota = null;
            dto.comentario = null;
        }
        if(hospedagem.getReserva()!=null)
            dto.idReserva = hospedagem.getReserva().getId();
        else
            dto.idReserva = null;

        dto.listaQuartos = QuartoHospedagemDTOList.createList(hospedagem.getQuartoHospedagem());
        dto.produtoHospedagem = ProdutoSolicitadoDTOList.createList(hospedagem.getProdutoSolicitado());
        // dto.produtoHospedagem = ServicoSolicitadoDTOList.createList(hospedagem.getSevicoSolicitado());

        //dto.status = hospedagem.getStatusHospedagem().getId();
        // dto.idHotel = hospedagem.getHotel().getId();
        // dto.idCliente = hospedagem.getCliente().getId();
        // dto.idFuncionario = hospedagem.getFuncionario().getId();
        // dto.idStatusHospedagem = hospedagem.getStatusHospedagem().getId();
        // dto.idTipoQuarto = hospedagem.getTipoQuarto().getId();
        // dto.idReserva = hospedagem.getReserva().getId();
        // dto.idAvaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem().getId();
        return dto;
    }
}
