package com.example.SCHapi.model.entity.Estadia.Lista;

import com.example.SCHapi.model.entity.Servico.HorarioServico;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelacaoHorarioServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidadeVagas;

    @ManyToOne
    private ServicoSolicitado servicoSolicitado;
    @ManyToOne
    private HorarioServico horarioServico;
}
