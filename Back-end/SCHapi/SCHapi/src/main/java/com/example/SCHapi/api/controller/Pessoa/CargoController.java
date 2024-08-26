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

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@CrossOrigin
public class CargoController {

    private final CargoService service;
    private final HotelService hotelService;

    @GetMapping()
    // @Operation(summary ="Obter a lista de cargo")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Cargo retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Cargo não encontrado")
    // })
    public ResponseEntity get() {
       List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um cargo")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Cargo encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Cargo não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }
    @PostMapping
    // @Operation(summary ="Salva um cargo")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Cargo salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Cargo"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um cargo")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Cargo alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Cargo não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um cargo")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Cargo excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Cargo não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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