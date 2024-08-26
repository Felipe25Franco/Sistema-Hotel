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

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoController {

    private final ProdutoService service;
    private final HotelService hotelService;
    private final TipoProdutoService tipoProdutoService;

    @GetMapping()
    // @Operation(summary ="Obter a lista de produto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Produto retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Produto não encontrado")
    // })
    public ResponseEntity get() {
       List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um produto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Produto encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Produto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id")  Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um produto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Produto salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Produto"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Atualiza um produto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Produto alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Produto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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
    // @Operation(summary ="Exclui um produto")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Produto excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Produto não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
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