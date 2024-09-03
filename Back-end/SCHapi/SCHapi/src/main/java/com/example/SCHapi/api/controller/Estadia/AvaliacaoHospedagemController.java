package com.example.SCHapi.api.controller.Estadia;

import com.example.SCHapi.api.dto.Estadia.AvaliacaoHospedagemDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.service.Estadia.AvaliacaoHospedagemService;

import io.swagger.annotations.*;

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
@Api("API de Avaliação de Hospedagem")
@CrossOrigin
public class AvaliacaoHospedagemController {

    private final AvaliacaoHospedagemService service;

    @GetMapping()
    @ApiOperation("Obter a lista de avaliação de uma hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Avaliação de uma hospedagem retornada com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma hospedagem não encontrado")
    })
    public ResponseEntity get() {
        System.out.println(AvaliacaoHospedagem.getMedia((long)1));
        List<AvaliacaoHospedagem> avaliacaoHospedagens = service.getAvaliacaoHospedagens();
        return ResponseEntity.ok(avaliacaoHospedagens.stream().map(AvaliacaoHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um avaliação de uma hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Avaliação de uma hospedagem encontrado"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma hospedagem não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Avaliação de Hospedagem") Long id) {
        Optional<AvaliacaoHospedagem> avaliacaoHospedagem = service.getAvaliacaoHospedagemById(id);
        if (!avaliacaoHospedagem.isPresent()) {
            return new ResponseEntity("AvaliacaoHospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacaoHospedagem.map(AvaliacaoHospedagemDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um avaliação de uma hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Avaliação de uma hospedagem salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Avaliação de uma hospedagem"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Atualiza um avaliação de uma hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Avaliação de uma hospedagem alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma hospedagem não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Exclui um avaliação de uma hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Avaliação de uma hospedagem excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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