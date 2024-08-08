package com.example.SCHapi.api.controller.Estadia.Lista;

import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Produto.Produto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.ProdutoSolicitadoService;
import com.example.SCHapi.service.Produto.ProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtoSolicitados")
@CrossOrigin
@RequiredArgsConstructor
public class ProdutoSolicitadoController {

    private final ProdutoSolicitadoService service;
    private final ProdutoService produtoService;
    private final HospedagemService hospedagemService;

    @GetMapping()
    public ResponseEntity get() {
       List<ProdutoSolicitado> produtoSolicitados = service.getProdutoSolicitados();
        return ResponseEntity.ok(produtoSolicitados.stream().map(ProdutoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ProdutoSolicitado> produtoSolicitado = service.getProdutoSolicitadoById(id);
        if (!produtoSolicitado.isPresent()) {
            return new ResponseEntity("ProdutoSolicitado não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoSolicitado.map(ProdutoSolicitadoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ProdutoSolicitadoDTO dto) {
        try {
            ProdutoSolicitado produtoSolicitado = converter(dto);
            produtoSolicitado = service.salvar(produtoSolicitado);
            return new ResponseEntity(produtoSolicitado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoSolicitadoDTO dto) {
        if (!service.getProdutoSolicitadoById(id).isPresent()) {
            return new ResponseEntity("ProdutoSolicitado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ProdutoSolicitado produtoSolicitado = converter(dto);
            produtoSolicitado.setId(id);
            System.out.println(dto);
            service.salvar(produtoSolicitado);
            return ResponseEntity.ok(produtoSolicitado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ProdutoSolicitado> produtoSolicitado = service.getProdutoSolicitadoById(id);
        if (!produtoSolicitado.isPresent()) {
            return new ResponseEntity("ProdutoSolicitado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produtoSolicitado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ProdutoSolicitado converter(ProdutoSolicitadoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoSolicitado produtoSolicitado = modelMapper.map(dto, ProdutoSolicitado.class);
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                produtoSolicitado.setProduto(null);
            } else {
                produtoSolicitado.setProduto(produto.get());
            }
        }
        if (dto.getIdHospedagem() != null) {
            Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(dto.getIdHospedagem());
            if (!hospedagem.isPresent()) {
                produtoSolicitado.setHospedagem(null);
            } else {
                produtoSolicitado.setHospedagem(hospedagem.get());
            }
        }
        return produtoSolicitado;
    }
}