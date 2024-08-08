package com.example.SCHapi.api.controller.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Pessoa.HotelDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Pessoa.Pais;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.service.Pessoa.EnderecoService;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Pessoa.PaisService;
import com.example.SCHapi.service.Pessoa.UfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hoteis")
@RequiredArgsConstructor
@CrossOrigin
public class HotelController {

    private final HotelService service;
    private final EnderecoService enderecoService;
    private final UfService ufService;
    private final PaisService paisService;

    @GetMapping()
    public ResponseEntity get() {
       List<Hotel> hoteis = service.getHoteis();
        return ResponseEntity.ok(hoteis.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if (!hotel.isPresent()) {
            return new ResponseEntity("Hotel não encontrada", HttpStatus.NOT_FOUND);
        }
        service.getMediaAvaliacao(hotel);
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody HotelDTO dto) {
        try {
            Hotel hotel = converter(dto);
            hotel = service.salvar(hotel);
            return new ResponseEntity(hotel, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HotelDTO dto) {
        if (!service.getHotelById(id).isPresent()) {
            return new ResponseEntity("Hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Hotel hotel = converter(dto);
            hotel.setId(id);
            Endereco endereco = hotel.getEndereco();
            endereco.setId(service.getHotelById(id).get().getEndereco().getId());
            endereco = enderecoService.salvar(endereco);
            hotel.setEndereco(endereco);
            System.out.println(dto);
            service.salvar(hotel);
            return ResponseEntity.ok(hotel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if (!hotel.isPresent()) {
            return new ResponseEntity("Hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(hotel.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Hotel converter(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        hotel.setTelefone1(dto.getDdi1().concat(dto.getDdd1()).concat(dto.getNum1()));
        hotel.setTelefone2(dto.getDdi2().concat(dto.getDdd2()).concat(dto.getNum2()));
        hotel.setEndereco(endereco);
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
        return hotel;
    }
}