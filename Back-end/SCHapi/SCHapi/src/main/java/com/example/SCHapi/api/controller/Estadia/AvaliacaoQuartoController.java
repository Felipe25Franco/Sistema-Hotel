package com.example.SCHapi.api.controller.Estadia;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Estadia.AvaliacaoQuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.service.Estadia.AvaliacaoQuartoService;
import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/avaliacaoQuartos")
@RequiredArgsConstructor
@Api("API de Avaliação de Quartos")
@CrossOrigin
public class AvaliacaoQuartoController {

    private final AvaliacaoQuartoService service;
    private final TipoQuartoService tipoQuartoService;
    private final HospedagemService hospedagemService;
    
    @GetMapping()
    @ApiOperation("Obter a lista de avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Avaliação de uma quarto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma quarto não encontrado")
    })
    public ResponseEntity get() {
       List<AvaliacaoQuarto> avaliacaoQuartos = service.getAvaliacaoQuartos();
        return ResponseEntity.ok(avaliacaoQuartos.stream().map(AvaliacaoQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Avaliação de uma quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma quarto não encontrado"),
            @ApiResponse(code =  500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Avaliação de Quarto")  Long id) {
        Optional<AvaliacaoQuarto> avaliacaoQuarto = service.getAvaliacaoQuartoById(id);
        if (!avaliacaoQuarto.isPresent()) {
            return new ResponseEntity("AvaliacaoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacaoQuarto.map(AvaliacaoQuartoDTO::create));
    }

    @GetMapping("/hospedagens/{id}")//retorna as avaliacoes da hospedagem do id
    @ApiOperation("Obter detalhes de um avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Avaliação de uma quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity getByHospedagemId(@PathVariable("id")  Long id) {
        Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem e AvaliacaoQuarto não encontrada", HttpStatus.NOT_FOUND);
        }
        List<AvaliacaoQuarto> avaliacaoQuartos = service.getAvaliacaoQuartoByHospedagem(hospedagem);
        return ResponseEntity.ok(avaliacaoQuartos.stream().map(AvaliacaoQuartoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation("Salva um avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Avaliação de uma quarto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Avaliação de uma quarto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody AvaliacaoQuartoDTO dto) {
        try {
            AvaliacaoQuarto avaliacaoQuarto = converter(dto);
            avaliacaoQuarto = service.salvar(avaliacaoQuarto);
            return new ResponseEntity(avaliacaoQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Avaliação de uma quarto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AvaliacaoQuartoDTO dto) {
        if (!service.getAvaliacaoQuartoById(id).isPresent()) {
            return new ResponseEntity("AvaliacaoQuarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AvaliacaoQuarto avaliacaoQuarto = converter(dto);
            avaliacaoQuarto.setId(id);
            System.out.println(dto);
            service.salvar(avaliacaoQuarto);
            return ResponseEntity.ok(avaliacaoQuarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um avaliação de uma quarto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Avaliação de uma quarto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Avaliação de uma quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AvaliacaoQuarto> avaliacaoQuarto = service.getAvaliacaoQuartoById(id);
        if (!avaliacaoQuarto.isPresent()) {
            return new ResponseEntity("Avaliação de Quarto não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(avaliacaoQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public AvaliacaoQuarto converter(AvaliacaoQuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoQuarto avaliacaoQuarto = modelMapper.map(dto, AvaliacaoQuarto.class);
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                avaliacaoQuarto.setTipoQuarto(null);
            } else {
                avaliacaoQuarto.setTipoQuarto(tipoQuarto.get());
            }
        }
        if (dto.getIdHospedagem() != null) {
            Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(dto.getIdHospedagem());
            if (!hospedagem.isPresent()) {
                avaliacaoQuarto.setHospedagem(null);
            } else {
                avaliacaoQuarto.setHospedagem(hospedagem.get());
            }
        }
        return avaliacaoQuarto;
    }
}