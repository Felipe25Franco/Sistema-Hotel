package com.example.SCHapi.service.Servico;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.repository.Servico.HorarioServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class HorarioServicoService {

    private HorarioServicoRepository repository;

    public HorarioServicoService(HorarioServicoRepository repository) {
        this.repository = repository;
    }

    public List<HorarioServico> getHorarioServicos() {
        return repository.findAll();
    }

    public Optional<HorarioServico> getHorarioServicoById(Long id) {
        return repository.findById(id);
    }

    // essa query é pra retornar a lista de todos os tipos quarto
    public List<HorarioServico> getHorarioServicoByServico(Optional<Servico> servico) {
        return repository.findByServico(servico);
    }

    @Transactional
    public HorarioServico salvar(HorarioServico horarioServico) {
        validar(horarioServico);
        return repository.save(horarioServico);
    }

    @Transactional
    public void excluir(HorarioServico horarioServico) {
        Objects.requireNonNull(horarioServico.getId());
        repository.delete(horarioServico);
    }

    public void validar(HorarioServico horarioServico) {


        // if (horarioServico.getHoraInicio() == null || horarioServico.getHoraInicio().trim().equals("") || !horarioServico.getHoraInicio().contains(":") || !horarioServico.getHoraInicio().matches("\\d{2}:\\d{2}$")) {
        //     throw new RegraNegocioException("Horario de entrada invalido   .");
        // }
        // if (horarioServico.getHoraFim() == null || horarioServico.getHoraFim().trim().equals("") || !horarioServico.getHoraFim().contains(":") || !horarioServico.getHoraFim().matches("\\d{2}:\\d{2}$")) {
        //     throw new RegraNegocioException("Horario de saida invalido   .");
        // }
       
        if (horarioServico.getServico() == null || horarioServico.getServico().getId() == null || horarioServico.getServico().getId() == 0) {
            throw new RegraNegocioException("Serviço inválido!!!!");
        }

        //na verdade deveria verificar se horarios se sobrepoe mas dps fazer
        List<HorarioServico> horarioServicos = getHorarioServicoByServico(Optional.of(horarioServico.getServico()));
        if(horarioServicos.stream().anyMatch((x) -> x.getData().equals(horarioServico.getData())&&x.getHoraInicio().equals(horarioServico.getHoraInicio())&&x.getHoraFim().equals(horarioServico.getHoraFim()))) {
            throw new RegraNegocioException("Horário duplicado. Selecione quartos distintos");
        }


    }
}