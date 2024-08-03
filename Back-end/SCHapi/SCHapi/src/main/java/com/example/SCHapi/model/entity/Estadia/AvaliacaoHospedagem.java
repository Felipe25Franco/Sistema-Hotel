package com.example.SCHapi.model.entity.Estadia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AvaliacaoHospedagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Float nota;
    private String comentario;

    public static Float getMedia(Long idHotel) {
        return (float) 0;
    }

}
