package com.example.SCHapi.model.repository;

import com.example.SCHapi.model.entity.TipoQuartoReserva;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Reserva;;



public interface TipoQuartoReservaRepository extends JpaRepository<TipoQuartoReserva, Long> {
    List<TipoQuartoReserva> findByReserva(Optional<Reserva> reserva);
}