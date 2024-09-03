package com.example.SCHapi.api.controller.Quarto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Quarto.StatusQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.Comodidade;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import com.example.SCHapi.service.Quarto.StatusQuartoService;

import io.swagger.annotations.*;

@RestController
@RequestMapping("/api/v1/statusQuartos")
@RequiredArgsConstructor
@Api("API de Status Quarto")
@CrossOrigin
public class StatusQuartoController {
    
    private final StatusQuartoService service;

    @GetMapping()
    @ApiOperation("Obter a lista de status de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Status de Quarto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Status de Quarto não encontrado")
    })
    public ResponseEntity get() {
       List<StatusQuarto> statusQuartos = service.getStatusQuarto();
        return ResponseEntity.ok(statusQuartos.stream().map(StatusQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um status de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Status de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Status Quarto")  Long id) {
        Optional<StatusQuarto> statusQuarto = service.getStatusQuartoById(id);
        if (!statusQuarto.isPresent()) {
            return new ResponseEntity("Status de Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusQuarto.map(StatusQuartoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um status de quarto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Status de Quarto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Status de Quarto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody StatusQuartoDTO dto) {
        try {
            StatusQuarto statusQuarto = converter(dto);
            statusQuarto = service.salvar(statusQuarto);
            return new ResponseEntity(statusQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um status de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Status de Quarto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody StatusQuartoDTO dto) {
        if (!service.getStatusQuartoById(id).isPresent()) {
            return new ResponseEntity("StatusQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            StatusQuarto statusQuarto = converter(dto);
            statusQuarto.setId(id);
            System.out.println(dto);
            service.salvar(statusQuarto);
            return ResponseEntity.ok(statusQuarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um status de quarto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Status de Quarto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Status de Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<StatusQuarto> statusQuarto = service.getStatusQuartoById(id);
        if (!statusQuarto.isPresent()) {
            return new ResponseEntity("Status de Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(statusQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public StatusQuarto converter(StatusQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusQuarto.class);
    }
}
