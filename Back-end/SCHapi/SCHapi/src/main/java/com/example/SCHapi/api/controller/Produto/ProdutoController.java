package com.example.SCHapi.api.controller.Produto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Produto.ProdutoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Produto.TipoProduto;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Produto.ProdutoService;
import com.example.SCHapi.service.Produto.TipoProdutoService;

import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@Api("API de Produto")
@CrossOrigin
public class ProdutoController {

    private final ProdutoService service;
    private final HotelService hotelService;
    private final TipoProdutoService tipoProdutoService;

    @GetMapping()
    @ApiOperation("Obter a lista de produto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Produto retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Produto não encontrado")
    })
    public ResponseEntity get() {
       List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Produto encontrado"),
            @ApiResponse(code  = 404, message  = "Produto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um produto")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Produto salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Produto"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody ProdutoDTO dto) {
        System.out.println(dto);
        try {
            Produto produto = converter(dto);
            produto = service.salvar(produto);            
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um produto")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Produto alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Produto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
        if (!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            System.out.println(dto);
            service.salvar(produto);
            return ResponseEntity.ok(produto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um produto")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Produto excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Produto não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        produto.setPrecoBase(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeestoque());
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                produto.setHotel(null);
            } else {
                produto.setHotel(hotel.get());
            }
        }
        if (dto.getIdTipoProduto() != null) {
            Optional<TipoProduto> tipoProduto = tipoProdutoService.getTipoProdutoById(dto.getIdTipoProduto());
            if (!tipoProduto.isPresent()) {
                produto.setTipoProduto(null);
            } else {
                produto.setTipoProduto(tipoProduto.get());
            }
        }
        return produto;
    }
}