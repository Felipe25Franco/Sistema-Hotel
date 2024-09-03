package com.example.SCHapi.api.controller.Quarto;

import com.example.SCHapi.api.dto.Quarto.QuartoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Quarto.QuartoService;
import com.example.SCHapi.service.Quarto.StatusQuartoService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

import io.swagger.annotations.*;

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
@Api("Api de Quarto")
@CrossOrigin
public class QuartoController {

    private final QuartoService service;
    private final HotelService hotelService;
    private final TipoQuartoService tipoquartoService;
    private final StatusQuartoService statusquartoService;

    @GetMapping()
    @ApiOperation("Obter a lista de quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Quarto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Quarto não encontrado")
    })
    public ResponseEntity get() {
       List<Quarto> quartos = service.getQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Quarto encontrado"),
            @ApiResponse(code  = 404, message  = "Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Quarto")  Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if (!quarto.isPresent()) {
            return new ResponseEntity("Quarto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um quarto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Quarto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Quarto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Atualiza um quarto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Quarto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Exclui um quarto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Quarto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Quarto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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