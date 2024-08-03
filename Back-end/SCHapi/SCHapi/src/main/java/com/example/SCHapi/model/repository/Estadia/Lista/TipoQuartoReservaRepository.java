package com.example.SCHapi.model.repository.Estadia.Lista;

import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;;



public interface TipoQuartoReservaRepository extends JpaRepository<TipoQuartoReserva, Long> {
    List<TipoQuartoReserva> findByReserva(Optional<Reserva> reserva);
}