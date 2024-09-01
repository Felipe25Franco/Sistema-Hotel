package com.example.SCHapi.api.controller.Servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Servico.HorarioServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.service.Servico.HorarioServicoService;
import com.example.SCHapi.service.Servico.ServicoService;

import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/v1/horarioServicos")
@RequiredArgsConstructor
@Api("API de Horario Serviço")
@CrossOrigin
public class HorarioServicoController {
    
    private final HorarioServicoService service;
    private final ServicoService servicoService;

    @GetMapping()
    @ApiOperation("Obter a lista de horário de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Horário de um Serviço retornada com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Horário de um Serviço não encontrado")
    })
    public ResponseEntity get() {
       List<HorarioServico> horarioServicos = service.getHorarioServicos();
        return ResponseEntity.ok(horarioServicos.stream().map(HorarioServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um horário de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Horário de um Serviço encontrado"),
            @ApiResponse(code  = 404, message  = "Horário de um Serviço não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<HorarioServico> horarioServico = service.getHorarioServicoById(id);
        if (!horarioServico.isPresent()) {
            return new ResponseEntity("Horario de Servico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(horarioServico.map(HorarioServicoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um horário de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Horário de um Serviço salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Horário de um Serviço"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody HorarioServicoDTO dto) {
        try {
            HorarioServico horarioServico = converter(dto);
            horarioServico = service.salvar(horarioServico);
            return new ResponseEntity(horarioServico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um horário de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Horário de um Serviço alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Horário de um Serviço não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody HorarioServicoDTO dto) {
        if (!service.getHorarioServicoById(id).isPresent()) {
            return new ResponseEntity("HorarioServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            HorarioServico horarioServico = converter(dto);
            horarioServico.setId(id);
            System.out.println(dto);
            service.salvar(horarioServico);
            return ResponseEntity.ok(horarioServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um horário de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Horário de um Serviço excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Horário de um Serviço não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<HorarioServico> horarioServico = service.getHorarioServicoById(id);
        if (!horarioServico.isPresent()) {
            return new ResponseEntity("horarioServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(horarioServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public HorarioServico converter(HorarioServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        HorarioServico horarioServico = modelMapper.map(dto, HorarioServico.class);
        if(dto.getIdServico() != null) {
            Optional<Servico> servico = servicoService.getServicoById(dto.getIdServico());
            if (!servico.isPresent()) {
                horarioServico.setServico(null);
            } else {
                horarioServico.setServico(servico.get());
            }
        }
        return horarioServico;
    }
}
