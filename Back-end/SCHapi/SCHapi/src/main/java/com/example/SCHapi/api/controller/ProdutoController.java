package com.example.SCHapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.ProdutoDTO;
import com.example.SCHapi.model.entity.Produto;
import com.example.SCHapi.model.entity.Hotel;
import com.example.SCHapi.service.ProdutoService;
import com.example.SCHapi.service.HotelService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;
    private final HotelService hotelService;

    @GetMapping()
    public ResponseEntity get() {
       List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                produto.setHotel(null);
            } else {
                produto.setHotel(hotel.get());
            }
        }
        return produto;
    }
}