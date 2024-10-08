package com.example.SCHapi.api.controller.Quarto;

import com.example.SCHapi.api.dto.Quarto.TipoCamaDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.service.Quarto.TipoCamaService;


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
@RequestMapping("/api/v1/tipoCamas")
@RequiredArgsConstructor
@Api("API de Tipo Cama")
@CrossOrigin
public class TipoCamaController {

    private final TipoCamaService service;
    
    @GetMapping()
    @ApiOperation("Obter a lista de tipo de cama")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Tipo de Cama retornada com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get() {
       List<TipoCama> tipoCamas = service.getTipoCamas();
        return ResponseEntity.ok(tipoCamas.stream().map(TipoCamaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um tipo de cama")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Cama encontrado"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Tipo Cama")  Long id) {
        Optional<TipoCama> tipoCama = service.getTipoCamaById(id);
        if (!tipoCama.isPresent()) {
            return new ResponseEntity("TipoCama não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoCama.map(TipoCamaDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um tipo de cama")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Tipo de Cama salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Tipo de Cama"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody TipoCamaDTO dto) {
        try {
            TipoCama tipoCama = converter(dto);
            tipoCama = service.salvar(tipoCama);
            return new ResponseEntity(tipoCama, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de cama")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Tipo de Cama alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody TipoCamaDTO dto) {
        if (!service.getTipoCamaById(id).isPresent()) {
            return new ResponseEntity("TipoCama não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoCama tipoCama = converter(dto);
            tipoCama.setId(id);
            System.out.println(dto);
            service.salvar(tipoCama);
            return ResponseEntity.ok(tipoCama);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de cama")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Tipo de Cama excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Tipo de Cama não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoCama> tipoCama = service.getTipoCamaById(id);
        if (!tipoCama.isPresent()) {
            return new ResponseEntity("Tipo de Cama não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoCama.get());
            return new ResponseEntity("TipoCama deletada", HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public TipoCama converter(TipoCamaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoCama.class);
    }
}