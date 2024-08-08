package com.example.SCHapi.api.controller.Produto;

import com.example.SCHapi.api.dto.Produto.TipoProdutoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Produto.TipoProduto;
import com.example.SCHapi.service.Produto.TipoProdutoService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipoProdutos")
@RequiredArgsConstructor
@CrossOrigin
public class TipoProdutoController {

    private final TipoProdutoService service;

    @GetMapping()
    public ResponseEntity get() {
       List<TipoProduto> tipoProdutos = service.getTipoProdutos();
        return ResponseEntity.ok(tipoProdutos.stream().map(TipoProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoProduto> tipoProduto = service.getTipoProdutoById(id);
        if (!tipoProduto.isPresent()) {
            return new ResponseEntity("TipoProduto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoProduto.map(TipoProdutoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoProdutoDTO dto) {
        try {
            TipoProduto tipoProduto = converter(dto);
            tipoProduto = service.salvar(tipoProduto);
            return new ResponseEntity(tipoProduto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoProdutoDTO dto) {
        if (!service.getTipoProdutoById(id).isPresent()) {
            return new ResponseEntity("TipoProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoProduto tipoProduto = converter(dto);
            tipoProduto.setId(id);
            System.out.println(dto);
            service.salvar(tipoProduto);
            return ResponseEntity.ok(tipoProduto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoProduto> tipoProduto = service.getTipoProdutoById(id);
        if (!tipoProduto.isPresent()) {
            return new ResponseEntity("TipoProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoProduto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    public TipoProduto converter(TipoProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoProduto.class);
    }
}