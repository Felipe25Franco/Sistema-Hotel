package com.example.SCHapi.api.controller.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Pessoa.CargoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Cargo;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.service.Pessoa.CargoService;
import com.example.SCHapi.service.Pessoa.HotelService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@Api("API de Cargos")
@CrossOrigin
public class CargoController {

    private final CargoService service;
    private final HotelService hotelService;

    @GetMapping()
    @ApiOperation("Obter a lista de cargo")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Cargo retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Cargo não encontrado")
    })
    public ResponseEntity get() {
       List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cargo")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Cargo encontrado"),
            @ApiResponse(code  = 404, message  = "Cargo não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Cargo")  Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }
    @PostMapping
    @ApiOperation("Salva um cargo")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Cargo salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Cargo"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody CargoDTO dto) {
        try {
            Cargo cargo = converter(dto);
            cargo = service.salvar(cargo);
            return new ResponseEntity(cargo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("{id}")
    @ApiOperation("Atualiza um cargo")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Cargo alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Cargo não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CargoDTO dto) {
        if (!service.getCargoById(id).isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cargo cargo = converter(dto);
            cargo.setId(id);
            System.out.println(dto);
            service.salvar(cargo);
            return ResponseEntity.ok(cargo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um cargo")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Cargo excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Cargo não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Cargo converter(CargoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cargo cargo = modelMapper.map(dto, Cargo.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                cargo.setHotel(null);
            } else {
                cargo.setHotel(hotel.get());
            }
        }
        return cargo;
    }
}