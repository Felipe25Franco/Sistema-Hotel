package com.example.SCHapi.api.controller.Servico;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Servico.StatusServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.StatusServico;
import com.example.SCHapi.service.Servico.StatusServicoService;

import io.swagger.annotations.*;

@RestController
@RequestMapping("/api/v1/statusServicos")
@RequiredArgsConstructor
@Api("API de Status Serviço")
@CrossOrigin
public class StatusServicoController {
    
    private final StatusServicoService service;

    @GetMapping()
    @ApiOperation("Obter a lista de status de serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Status de Serviço retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Status de Serviço não encontrado")
    })
    public ResponseEntity get() {
       List<StatusServico> statusServicos = service.getStatusServico();
        return ResponseEntity.ok(statusServicos.stream().map(StatusServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um status de serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Serviço encontrado"),
            @ApiResponse(code  = 404, message  = "Status de Serviço não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Status Serviço") Long id) {
        Optional<StatusServico> statusServico = service.getStatusServicoById(id);
        if (!statusServico.isPresent()) {
            return new ResponseEntity("Status de Servico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusServico.map(StatusServicoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um status de serviço")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Status de Serviço salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Status de Serviço"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody StatusServicoDTO dto) {
        try {
            StatusServico statusServico = converter(dto);
            statusServico = service.salvar(statusServico);
            return new ResponseEntity(statusServico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um status de serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Serviço alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Serviço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody StatusServicoDTO dto) {
        if (!service.getStatusServicoById(id).isPresent()) {
            return new ResponseEntity("StatusServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            StatusServico statusServico = converter(dto);
            statusServico.setId(id);
            System.out.println(dto);
            service.salvar(statusServico);
            return ResponseEntity.ok(statusServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um status de serviço")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Status de Serviço excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Serviço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<StatusServico> statusServico = service.getStatusServicoById(id);
        if (!statusServico.isPresent()) {
            return new ResponseEntity("statusServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(statusServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public StatusServico converter(StatusServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusServico.class);
    }
}
