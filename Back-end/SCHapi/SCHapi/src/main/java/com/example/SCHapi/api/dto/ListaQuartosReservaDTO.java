package com.example.SCHapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListaQuartosReservaDTO {
    private Long id;
    private Integer qtd;
    private Long idTipoQuarto;////////
}
