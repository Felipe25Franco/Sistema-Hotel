package com.example.SCHapi.model.entity.Estadia;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.Date;
import java.util.List;

import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospedagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dataInicio;
    private String dataFim1;
    private String dataFim2;
    private String statusValorEstadia;
    private Float valorEstadia;
    private Float valorConsumo;
    private Float valorServicos;
    private Float valorEstadiaAdicional;
    private Float valorTotalPago;


    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private StatusHospedagem statusHospedagem;



    @OneToOne
    private Reserva reserva;
    @OneToOne(cascade = CascadeType.ALL)
    private AvaliacaoHospedagem AvaliacaoHospedagem;

    @JsonIgnore
    @OneToMany (mappedBy = "hospedagem")
    private List<QuartoHospedagem> quartoHospedagem;
    @JsonIgnore
    @OneToMany (mappedBy = "hospedagem")
    private List<ServicoSolicitado> sevicoSolicitado;
    @JsonIgnore
    @OneToMany (mappedBy = "hospedagem")
    private List<ProdutoSolicitado> produtoSolicitado;
    @JsonIgnore
    @OneToMany (mappedBy = "hospedagem")
    private List<AvaliacaoQuarto> avaliacaoQuarto;

}
