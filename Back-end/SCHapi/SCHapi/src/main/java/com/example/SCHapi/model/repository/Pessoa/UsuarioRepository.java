package com.example.SCHapi.model.repository.Pessoa;

import com.example.SCHapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}