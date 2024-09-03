package com.example.SCHapi.api.controller.Estadia.Lista;

import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.ProdutoSolicitadoService;
import com.example.SCHapi.service.Produto.ProdutoService;

import io.swagger.annotations.*; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtoSolicitados")
@RequiredArgsConstructor
@Api("API de Produto Solicitado")
@CrossOrigin
public class ProdutoSolicitadoController {

    private final ProdutoSolicitadoService service;
    private final ProdutoService produtoService;
    private final HospedagemService hospedagemService;

    @GetMapping()
    @ApiOperation("Obter a lista de produto solicitado da hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Produto Solicitado da Hospedagem retornada com sucesso"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Produto Solicitado da Hospedagem não encontrado")
    })
    public ResponseEntity get() {
       List<ProdutoSolicitado> produtoSolicitados = service.getProdutoSolicitados();
        return ResponseEntity.ok(produtoSolicitados.stream().map(ProdutoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto solicitado da hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Produto Solicitado da Hospedagem encontrado"),
            @ApiResponse(code  = 404, message  = "Produto Solicitado da Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Produto Solicitado")  Long id) {
        Optional<ProdutoSolicitado> produtoSolicitado = service.getProdutoSolicitadoById(id);
        if (!produtoSolicitado.isPresent()) {
            return new ResponseEntity("ProdutoSolicitado não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoSolicitado.map(ProdutoSolicitadoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um produto solicitado da hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Produto Solicitado da Hospedagem salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Produto Solicitado da Hospedagem"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation("Atualiza um produto solicitado da hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Produto Solicitado da Hospedagem alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Produto Solicitado da Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id")  Long id, @RequestBody ProdutoSolicitadoDTO dto) {
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
    @ApiOperation("Exclui um produto solicitado da hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Produto Solicitado da Hospedagem excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Produto Solicitado da Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
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