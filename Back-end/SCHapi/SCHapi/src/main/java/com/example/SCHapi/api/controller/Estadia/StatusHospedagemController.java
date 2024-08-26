package com.example.SCHapi.api.controller.Estadia;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.Estadia.StatusHospedagemDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Estadia.StatusReserva;
import com.example.SCHapi.service.Estadia.StatusHospedagemService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/statusHospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class StatusHospedagemController {
    
    private final StatusHospedagemService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de status de hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Status de Hospedagem retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Status de Hospedagem não encontrado")
    // })
    public ResponseEntity get() {
       List<StatusHospedagem> statusHospedagems = service.getStatusHospedagem();
        return ResponseEntity.ok(statusHospedagems.stream().map(StatusHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um status de hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Hospedagem encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<StatusHospedagem> statusHospedagem = service.getStatusHospedagemById(id);
        if (!statusHospedagem.isPresent()) {
            return new ResponseEntity("Status de Hospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusHospedagem.map(StatusHospedagemDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um status de hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Status de Hospedagem salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Status de Hospedagem"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody StatusHospedagemDTO dto) {
        try {
            StatusHospedagem statusHospedagem = converter(dto);
            statusHospedagem = service.salvar(statusHospedagem);
            return new ResponseEntity(statusHospedagem, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um status de hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Hospedagem alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody StatusHospedagemDTO dto) {
        if (!service.getStatusHospedagemById(id).isPresent()) {
            return new ResponseEntity("StatusHospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            StatusHospedagem statusHospedagem = converter(dto);
            statusHospedagem.setId(id);
            System.out.println(dto);
            service.salvar(statusHospedagem);
            return ResponseEntity.ok(statusHospedagem);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um status de hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Status de Hospedagem excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<StatusHospedagem> statusHospedagem = service.getStatusHospedagemById(id);
        if (!statusHospedagem.isPresent()) {
            return new ResponseEntity("Status de Hospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(statusHospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public StatusHospedagem converter(StatusHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusHospedagem.class);
    }
}
