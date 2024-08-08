package com.example.SCHapi.api.controller.Servico.Lista;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.SCHapi.api.dto.Servico.Lista.RelacaoHorarioServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;
import com.example.SCHapi.service.Estadia.Lista.ServicoSolicitadoService;
import com.example.SCHapi.service.Servico.HorarioServicoService;
import com.example.SCHapi.service.Servico.Lista.RelacaoHorarioServicoService;

@RestController
@RequestMapping("/api/v1/relacaoHorarioServicos")
@CrossOrigin
@RequiredArgsConstructor
public class RelacaoHorarioServicoController {

    private final RelacaoHorarioServicoService service;
    private final HorarioServicoService horarioServicoService;
    private final ServicoSolicitadoService servicoSolicitadoService;
    
    @GetMapping()
    public ResponseEntity get() {
       List<RelacaoHorarioServico> relacaoHorarioServicos = service.getRelacaoHorariosServico();
        return ResponseEntity.ok(relacaoHorarioServicos.stream().map(RelacaoHorarioServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<RelacaoHorarioServico> relacaoHorarioServico = service.getRelacaoHorariosServicoById(id);
        if (!relacaoHorarioServico.isPresent()) {
            return new ResponseEntity("RelaçãoHorarioServiço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(relacaoHorarioServico.map(RelacaoHorarioServicoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody RelacaoHorarioServicoDTO dto) {
        try {
            RelacaoHorarioServico relacaoHorarioServico = converter(dto);
            relacaoHorarioServico = service.salvar(relacaoHorarioServico);
            return new ResponseEntity(relacaoHorarioServico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody RelacaoHorarioServicoDTO dto) {
        if (!service.getRelacaoHorariosServicoById(id).isPresent()) {
            return new ResponseEntity("RelacaoHorarioServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            RelacaoHorarioServico relacaoHorarioServico = converter(dto);
            relacaoHorarioServico.setId(id);
            System.out.println(dto);
            service.salvar(relacaoHorarioServico);
            return ResponseEntity.ok(relacaoHorarioServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<RelacaoHorarioServico> relacaoHorarioServico = service.getRelacaoHorariosServicoById(id);
        if (!relacaoHorarioServico.isPresent()) {
            return new ResponseEntity("Relação de Horario e Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(relacaoHorarioServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public RelacaoHorarioServico converter(RelacaoHorarioServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        RelacaoHorarioServico relacaoHorarioServico = modelMapper.map(dto, RelacaoHorarioServico.class);
        if (dto.getIdHorarioServico() != null) {
            Optional<HorarioServico> horarioServico = horarioServicoService.getHorarioServicoById(dto.getIdHorarioServico());
            if (!horarioServico.isPresent()) {
                relacaoHorarioServico.setHorarioServico(null);
            } else {
                relacaoHorarioServico.setHorarioServico(horarioServico.get());
            }
        }
        if (dto.getIdServicoSolicitado() != null) {
            Optional<ServicoSolicitado> servicoSolicitado = servicoSolicitadoService.getServicoSolicitadoById(dto.getIdServicoSolicitado());
            if (!servicoSolicitado.isPresent()) {
                relacaoHorarioServico.setServicoSolicitado(null);
            } else {
                relacaoHorarioServico.setServicoSolicitado(servicoSolicitado.get());
            }
        }
        return relacaoHorarioServico;
    }
}
