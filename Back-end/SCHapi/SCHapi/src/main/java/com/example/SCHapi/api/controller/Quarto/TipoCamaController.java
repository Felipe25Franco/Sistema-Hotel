package com.example.SCHapi.api.controller.Quarto;

import com.example.SCHapi.api.dto.Quarto.TipoCamaDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.service.Quarto.TipoCamaService;

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
@RequestMapping("/api/v1/tipoCamas")
@RequiredArgsConstructor
@CrossOrigin
public class TipoCamaController {

    private final TipoCamaService service;
    
    @GetMapping()
    // @Operation(summary ="Obter a lista de tipo de cama")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Tipo de Cama retornada com sucesso"),
    //         //@ApiResponse(responseCode  = "404", description  = "Tipo de Cama não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get() {
       List<TipoCama> tipoCamas = service.getTipoCamas();
        return ResponseEntity.ok(tipoCamas.stream().map(TipoCamaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um tipo de cama")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Tipo de Cama encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Cama não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<TipoCama> tipoCama = service.getTipoCamaById(id);
        if (!tipoCama.isPresent()) {
            return new ResponseEntity("TipoCama não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoCama.map(TipoCamaDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um tipo de cama")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Tipo de Cama salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Tipo de Cama"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um tipo de cama")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Tipo de Cama alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Cama não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um tipo de cama")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Tipo de Cama excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Tipo de Cama não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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