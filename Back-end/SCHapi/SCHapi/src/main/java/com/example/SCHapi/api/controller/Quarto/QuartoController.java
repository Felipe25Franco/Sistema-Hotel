package com.example.SCHapi.api.controller.Quarto;

import com.example.SCHapi.api.dto.Quarto.QuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Quarto.QuartoService;
import com.example.SCHapi.service.Quarto.StatusQuartoService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quartos")
@RequiredArgsConstructor
@CrossOrigin
public class QuartoController {

    private final QuartoService service;
    private final HotelService hotelService;
    private final TipoQuartoService tipoquartoService;
    private final StatusQuartoService statusquartoService;

    @GetMapping()
    // @Operation(summary ="Obter a lista de quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Quarto retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Quarto não encontrado")
    // })
    public ResponseEntity get() {
       List<Quarto> quartos = service.getQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Quarto encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if (!quarto.isPresent()) {
            return new ResponseEntity("Quarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Quarto salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Quarto"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody QuartoDTO dto) {
        try {
            Quarto quarto = converter(dto);
            quarto = service.salvar(quarto);
            return new ResponseEntity(quarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Quarto alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody QuartoDTO dto) {
        if (!service.getQuartoById(id).isPresent()) {
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Quarto quarto = converter(dto);
            quarto.setId(id);
            System.out.println(dto);
            service.salvar(quarto);
            return ResponseEntity.ok(quarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um quarto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Quarto excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Quarto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if (!quarto.isPresent()) {
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(quarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Quarto converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                quarto.setHotel(null);
            } else {
                quarto.setHotel(hotel.get());
            }
        }
        if (dto.getIdTipoQuarto() != null) {
            Optional<TipoQuarto> tipoquarto = tipoquartoService.getTipoQuartoById(dto.getIdTipoQuarto());
            if (!tipoquarto.isPresent()) {
                quarto.setTipoQuarto(null);
            } else {
                quarto.setTipoQuarto(tipoquarto.get());
            }
        }
        if (dto.getStatus() != null) {
            Optional<StatusQuarto> statusquarto = statusquartoService.getStatusQuartoById(dto.getStatus());
            if (!statusquarto.isPresent()) {
                quarto.setStatusQuarto(null);
            } else {
                quarto.setStatusQuarto(statusquarto.get());
            }
        }
        return quarto;
    }
}