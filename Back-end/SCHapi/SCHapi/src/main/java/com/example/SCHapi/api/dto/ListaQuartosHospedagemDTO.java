package com.example.SCHapi.api.dto;

import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListaQuartosHospedagemDTO {
    private Long id;
    private Integer qtd;
    private Float idQuarto;
    private Long idTipoQuarto;////////



}
