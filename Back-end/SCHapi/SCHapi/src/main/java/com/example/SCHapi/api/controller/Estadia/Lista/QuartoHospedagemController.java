package com.example.SCHapi.api.controller.Estadia.Lista;

import com.example.SCHapi.api.dto.Estadia.Lista.QuartoHospedagemDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Quarto.Quarto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.QuartoHospedagemService;
import com.example.SCHapi.service.Quarto.QuartoService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quartoHospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class QuartoHospedagemController {

    private final QuartoHospedagemService service;
    private final QuartoService quartoService;
    private final HospedagemService hospedagemService;

    @GetMapping()
    // @Operation(summary ="Obter a lista de quarto da hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Quarto da Hospedagem retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Quarto da Hospedagem não encontrado")
    // })
    public ResponseEntity get() {
       List<QuartoHospedagem> quartoHospedagems = service.getQuartoHospedagens();
        return ResponseEntity.ok(quartoHospedagems.stream().map(QuartoHospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um quarto da hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Quarto da Hospedagem encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto da Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<QuartoHospedagem> quartoHospedagem = service.getQuartoHospedagemById(id);
        if (!quartoHospedagem.isPresent()) {
            return new ResponseEntity("QuartoHospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quartoHospedagem.map(QuartoHospedagemDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um quarto da hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Quarto da Hospedagem salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Quarto da Hospedagem"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody QuartoHospedagemDTO dto) {
        try {
            QuartoHospedagem quartoHospedagem = converter(dto);
            quartoHospedagem = service.salvar(quartoHospedagem);
            return new ResponseEntity(quartoHospedagem, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um quarto da hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Quarto da Hospedagem alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto da Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody QuartoHospedagemDTO dto) {
        if (!service.getQuartoHospedagemById(id).isPresent()) {
            return new ResponseEntity("QuartoHospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            QuartoHospedagem quartoHospedagem = converter(dto);
            quartoHospedagem.setId(id);
            System.out.println(dto);
            service.salvar(quartoHospedagem);
            return ResponseEntity.ok(quartoHospedagem);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um quarto da hospedagem")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Quarto da Hospedagem excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto da Hospedagem não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<QuartoHospedagem> quartoHospedagem = service.getQuartoHospedagemById(id);
        if (!quartoHospedagem.isPresent()) {
            return new ResponseEntity("QuartoHospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(quartoHospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public QuartoHospedagem converter(QuartoHospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoHospedagem quartoHospedagem = modelMapper.map(dto, QuartoHospedagem.class);
        if (dto.getIdQuarto() != null) {
            Optional<Quarto> quarto = quartoService.getQuartoById(dto.getIdQuarto());
            if (!quarto.isPresent()) {
                quartoHospedagem.setQuarto(null);
            } else {
                quartoHospedagem.setQuarto(quarto.get());
            }
        }
        if (dto.getIdHospedagem() != null) {
            Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(dto.getIdHospedagem());
            if (!hospedagem.isPresent()) {
                quartoHospedagem.setHospedagem(null);
            } else {
                quartoHospedagem.setHospedagem(hospedagem.get());
            }
        }
        return quartoHospedagem;
    }
}