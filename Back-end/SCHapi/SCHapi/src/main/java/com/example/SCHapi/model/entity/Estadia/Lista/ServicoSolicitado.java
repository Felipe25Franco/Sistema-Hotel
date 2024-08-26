package com.example.SCHapi.model.entity.Estadia.Lista;

import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float valorTotal;


    @ManyToOne
    private Servico servico;
    @ManyToOne
    private Hospedagem hospedagem;

    @JsonIgnore
    @OneToMany (mappedBy = "servicoSolicitado")
    //@NotFound(action = NotFoundAction.IGNORE)
    private List<RelacaoHorarioServico> relacaoHorarioServico;
}
