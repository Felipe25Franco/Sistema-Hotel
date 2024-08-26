package com.example.SCHapi.model.repository.Servico;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.entity.Servico.TipoServico;



public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByTipoServico(Optional<TipoServico> TipoServico);

}