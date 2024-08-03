package com.example.SCHapi.model.repository.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface QuartoHospedagemRepository extends JpaRepository<QuartoHospedagem, Long> {
    List<QuartoHospedagem> findByHospedagem(Optional<Hospedagem> hospedagem);

}