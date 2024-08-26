package com.example.SCHapi.api.controller.Estadia;

import com.example.SCHapi.api.dto.Estadia.AvaliacaoHospedagemDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.service.Estadia.AvaliacaoHospedagemService;

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
@RequestMapping("/api/v1/avaliacaoHospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class AvaliacaoHospedagemController {

    private final AvaliacaoHospedagemService service;

    @GetMapping()
    // @Operation(summary ="Obter a lista de avaliação de uma hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Avaliação de uma hospedagem retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Avaliação de uma hospedagem não encontrado")
    // })
    public ResponseEntity get() {
        System.out.println(AvaliacaoHospedagem.getMedia((long)1));
        List<AvaliacaoHospedagem> avaliacaoHospedagens = service.getAvaliacaoHospedagens();
        return ResponseEntity.ok(avaliacaoHospedagens.stream().map(AvaliacaoHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um avaliação de uma hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Avaliação de uma hospedagem encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Avaliação de uma hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AvaliacaoHospedagem> avaliacaoHospedagem = service.getAvaliacaoHospedagemById(id);
        if (!avaliacaoHospedagem.isPresent()) {
            return new ResponseEntity("AvaliacaoHospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacaoHospedagem.map(AvaliacaoHospedagemDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um avaliação de uma hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Avaliação de uma hospedagem salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Avaliação de uma hospedagem"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody AvaliacaoHospedagemDTO dto) {
        try {
            AvaliacaoHospedagem avaliacaoHospedagem = converter(dto);
            avaliacaoHospedagem = service.salvar(avaliacaoHospedagem);
            return new ResponseEntity(avaliacaoHospedagem, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um avaliação de uma hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Avaliação de uma hospedagem alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Avaliação de uma hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id")Long id, @RequestBody AvaliacaoHospedagemDTO dto) {
        if (!service.getAvaliacaoHospedagemById(id).isPresent()) {
            return new ResponseEntity("AvaliacaoHospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AvaliacaoHospedagem avaliacaoHospedagem = converter(dto);
            avaliacaoHospedagem.setId(id);
            System.out.println(dto);
            service.salvar(avaliacaoHospedagem);
            return ResponseEntity.ok(avaliacaoHospedagem);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um avaliação de uma hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Avaliação de uma hospedagem excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Avaliação de uma hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<AvaliacaoHospedagem> avaliacaoHospedagem = service.getAvaliacaoHospedagemById(id);
        if (!avaliacaoHospedagem.isPresent()) {
            return new ResponseEntity("Avaliação de Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(avaliacaoHospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AvaliacaoHospedagem converter(AvaliacaoHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoHospedagem avaliacaohospedagem = modelMapper.map(dto, AvaliacaoHospedagem.class);
        
        return avaliacaohospedagem;
    }
}