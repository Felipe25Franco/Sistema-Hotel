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

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/statusQuartos")
@RequiredArgsConstructor
@CrossOrigin
public class StatusQuartoController {
    
    private final StatusQuartoService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de status de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Status de Quarto retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Status de Quarto não encontrado")
    // })
    public ResponseEntity get() {
       List<StatusQuarto> statusQuartos = service.getStatusQuarto();
        return ResponseEntity.ok(statusQuartos.stream().map(StatusQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um status de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Quarto encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<StatusQuarto> statusQuarto = service.getStatusQuartoById(id);
        if (!statusQuarto.isPresent()) {
            return new ResponseEntity("Status de Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusQuarto.map(StatusQuartoDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um status de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Status de Quarto salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Status de Quarto"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um status de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Quarto alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um status de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Status de Quarto excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
