package com.example.SCHapi.model.entity.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuartoReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer qtd;

    @ManyToOne
    private Reserva reserva;
    @ManyToOne
    private TipoQuarto tipoQuarto;
}
