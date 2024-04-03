package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.FuncionarioDTO;
import com.example.SCHapi.model.entity.Funcionario;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.model.entity.Pais;
import com.example.SCHapi.model.entity.Cargo;
import com.example.SCHapi.model.entity.Endereco;
import com.example.SCHapi.model.entity.Uf;
import com.example.SCHapi.service.CargoService;
import com.example.SCHapi.service.EnderecoService;
import com.example.SCHapi.service.FuncionarioService;
import com.example.SCHapi.service.HotelService;
import com.example.SCHapi.service.PaisService;
import com.example.SCHapi.service.UfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;
    private final EnderecoService enderecoService;
    private final UfService ufService;
    private final PaisService paisService;
    private final HotelService hotelService;
    private final CargoService cargoService;

    @GetMapping()
    public ResponseEntity get() {
       List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        funcionario.setEndereco(endereco);
        if (dto.getIdUf() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            if (!uf.isPresent()) {
                endereco.setUf(null);
            } else {
                endereco.setUf(uf.get());
            }
        }
        if (dto.getIdPais() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            Optional<Pais> pais = paisService.getPaisById(dto.getIdPais());
            if (!pais.isPresent()) {
                uf.get().setPais(null);
            } else {
                uf.get().setPais(pais.get());
            }
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                funcionario.setHotel(null);
            } else {
                funcionario.setHotel(hotel.get());
            }
        }
        if (dto.getIdCargo() != null) {
            Optional<Cargo> cargo = cargoService.getCargoById(dto.getIdCargo());
            if (!cargo.isPresent()) {
                funcionario.setCargo(null);
            } else {
                funcionario.setCargo(cargo.get());
            }
        }
        return funcionario;
    }
}