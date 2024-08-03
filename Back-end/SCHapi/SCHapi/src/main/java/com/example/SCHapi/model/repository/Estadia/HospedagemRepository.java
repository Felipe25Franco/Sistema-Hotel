package com.example.SCHapi.model.repository.Estadia;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Pessoa.Hotel;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {
    Hospedagem findByReserva(Optional<Reserva> reserva);
    List<Hospedagem> findByHotel(Optional<Hotel> hotel);
}
