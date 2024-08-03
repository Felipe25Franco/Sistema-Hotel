package com.example.SCHapi.model.repository.Quarto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.Quarto;



public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByHotel(Optional<Hotel> hotel);

}