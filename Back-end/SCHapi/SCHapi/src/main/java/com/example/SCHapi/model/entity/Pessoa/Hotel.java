package com.example.SCHapi.model.entity.Pessoa;

import java.util.List;
import java.util.Optional;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Float avaliacaoMedia;
    private String telefone1;
    private String telefone2;



    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<Hospedagem> hospedagens;

    public Float getMediaAvaliacao() {
        //List<Hospedagem> hospedagens = hospedagemService.getHospedagemByHotel(hotel);
        //hospedagens.stream().map(x->x.getAvaliacaoHospedagem().getNota()).toList();
        if (this.hospedagens.isEmpty()) return (float) -1;
        Float avaliacaoMedia = (float)0;
        Float i = (float)0;
        for(Hospedagem hospedagem : this.hospedagens) {
            if (hospedagem.getAvaliacaoHospedagem().getNota() >= 0) {
                avaliacaoMedia += hospedagem.getAvaliacaoHospedagem().getNota();
                i++;
            }
        }
        //System.out.println(hospedagens);
        //System.out.println(hospedagens.stream());
        return avaliacaoMedia/i;
    }
}
