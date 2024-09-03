package com.example.SCHapi.api.controller.Estadia.Lista;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Estadia.Lista.RelacaoHorarioServicoDTO;
import com.example.SCHapi.api.dto.Quarto.Lista.ComodidadeTipoQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.service.Estadia.Lista.RelacaoHorarioServicoService;
import com.example.SCHapi.service.Estadia.Lista.ServicoSolicitadoService;
import com.example.SCHapi.service.Servico.HorarioServicoService;

import io.swagger.annotations.*; 

@RestController
@RequestMapping("/api/v1/relacaoHorarioServicos")
@RequiredArgsConstructor
@Api("API de Relação Horario Serviço")
@CrossOrigin
public class RelacaoHorarioServicoController {

    private final RelacaoHorarioServicoService service;
    private final HorarioServicoService horarioServicoService;
    private final ServicoSolicitadoService servicoSolicitadoService;
    
    @GetMapping()
    @ApiOperation("Obter a lista de horário reservado do serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Horário reservado do serviço solicitado retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Horário reservado do serviço solicitado não encontrado")
    })
    public ResponseEntity get() {
       List<RelacaoHorarioServico> relacaoHorarioServicos = service.getRelacaoHorariosServico();
        return ResponseEntity.ok(relacaoHorarioServicos.stream().map(RelacaoHorarioServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um horário reservado do serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Horário reservado do serviço solicitado encontrado"),
            @ApiResponse(code  = 404, message  = "Horário reservado do serviço solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Relação Horario Serviço")  Long id) {
        Optional<RelacaoHorarioServico> relacaoHorarioServico = service.getRelacaoHorariosServicoById(id);
        if (!relacaoHorarioServico.isPresent()) {
            return new ResponseEntity("RelaçãoHorarioServiço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(relacaoHorarioServico.map(RelacaoHorarioServicoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um horário reservado do serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Horário reservado do serviço solicitado salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Horário reservado do serviço solicitado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Atualiza um horário reservado do serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Horário reservado do serviço solicitado alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Horário reservado do serviço solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Exclui um horário reservado do serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Horário reservado do serviço solicitado excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Horário reservado do serviço solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
