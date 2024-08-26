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

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/statusServicos")
@RequiredArgsConstructor
@CrossOrigin
public class StatusServicoController {
    
    private final StatusServicoService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de status de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Status de Serviço retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Status de Serviço não encontrado")
    // })
    public ResponseEntity get() {
       List<StatusServico> statusServicos = service.getStatusServico();
        return ResponseEntity.ok(statusServicos.stream().map(StatusServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um status de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Serviço encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<StatusServico> statusServico = service.getStatusServicoById(id);
        if (!statusServico.isPresent()) {
            return new ResponseEntity("Status de Servico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusServico.map(StatusServicoDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um status de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Status de Serviço salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Status de Serviço"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um status de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Status de Serviço alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um status de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Status de Serviço excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Status de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
