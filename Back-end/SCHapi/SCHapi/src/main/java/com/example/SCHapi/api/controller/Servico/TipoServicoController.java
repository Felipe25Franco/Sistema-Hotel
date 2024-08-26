package com.example.SCHapi.api.controller.Servico;

import com.example.SCHapi.api.dto.Servico.TipoServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Servico.TipoServico;
import com.example.SCHapi.service.Servico.TipoServicoService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoServicos")
@RequiredArgsConstructor
@CrossOrigin
public class TipoServicoController {

    private final TipoServicoService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de tipo de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Tipo de Serviço retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Tipo de Serviço não encontrado")
    // })
    public ResponseEntity get() {
       List<TipoServico> tipoServicos = service.getTipoServicos();
        return ResponseEntity.ok(tipoServicos.stream().map(TipoServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um tipo de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Tipo de Serviço encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<TipoServico> tipoServico = service.getTipoServicoById(id);
        if (!tipoServico.isPresent()) {
            return new ResponseEntity("TipoServico não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoServico.map(TipoServicoDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um tipo de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Tipo de Serviço salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Tipo de Serviço"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody TipoServicoDTO dto) {
        try {
            TipoServico tipoServico = converter(dto);
            tipoServico = service.salvar(tipoServico);
            return new ResponseEntity(tipoServico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um tipo de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Tipo de Serviço alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoServicoDTO dto) {
        if (!service.getTipoServicoById(id).isPresent()) {
            return new ResponseEntity("TipoServico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoServico tipoServico = converter(dto);
            tipoServico.setId(id);
            System.out.println(dto);
            service.salvar(tipoServico);
            return ResponseEntity.ok(tipoServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um tipo de serviço")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Tipo de Serviço excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Serviço não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoServico> tipoServico = service.getTipoServicoById(id);
        if (!tipoServico.isPresent()) {
            return new ResponseEntity("Tipo de Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoServico converter(TipoServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoServico.class);
    }
}