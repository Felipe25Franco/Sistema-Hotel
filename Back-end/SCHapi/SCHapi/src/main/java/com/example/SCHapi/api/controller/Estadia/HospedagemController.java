package com.example.SCHapi.api.controller.Estadia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.Estadia.HospedagemDTO;
import com.example.SCHapi.api.dto.Estadia.ReservaDTO;
import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.QuartoHospedagemDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTOList;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoHospedagem;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.service.Estadia.AvaliacaoHospedagemService;
import com.example.SCHapi.service.Estadia.AvaliacaoQuartoService;
import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.ReservaService;
import com.example.SCHapi.service.Estadia.StatusHospedagemService;
import com.example.SCHapi.service.Estadia.Lista.ProdutoSolicitadoService;
import com.example.SCHapi.service.Estadia.Lista.QuartoHospedagemService;
import com.example.SCHapi.service.Estadia.Lista.TipoQuartoReservaService;
import com.example.SCHapi.service.Pessoa.ClienteService;
import com.example.SCHapi.service.Pessoa.FuncionarioService;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Produto.ProdutoService;
import com.example.SCHapi.service.Quarto.QuartoService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

import io.swagger.annotations.*;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospedagens")
@RequiredArgsConstructor
@Api("API de Hospedagem")
@CrossOrigin
public class HospedagemController {
    private final HospedagemService service;
    private final ClienteService clienteService;
    private final HotelService hotelService;
    private final FuncionarioService funcionarioService;
    private final StatusHospedagemService statushospedagemService;
    private final AvaliacaoHospedagemService avaliacaoHospedagemService;
    private final ProdutoSolicitadoService produtoSolicitadoService;
    private final QuartoHospedagemService quartoHospedagemService;
    private final QuartoService quartoService;
    private final ProdutoService produtoService;
    private final AvaliacaoQuartoService avaliacaoQuartoService;
    private final ReservaService reservaService;
    private final TipoQuartoReservaService tipoQuartoReservaService;

    @GetMapping()
    @ApiOperation("Obter a lista de hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Hospedagem retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Hospedagem não encontrado")
    })
    public ResponseEntity get() {
       List<Hospedagem> hospedagens = service.getHospedagens();
        return ResponseEntity.ok(hospedagens.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }


    
    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Hospedagem encontrado"),
            @ApiResponse(code  = 404, message  = "Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Hospedagem")  Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        List<QuartoHospedagem> listaQuartos = quartoHospedagemService.getQuartoHospedagemByHospedagem(hospedagem);
        List<ProdutoSolicitado> produtoSolicitado = produtoSolicitadoService.getProdutoSolicitadoByHospedagem(hospedagem);
        HospedagemDTO hospedagemDTO = new HospedagemDTO();
        hospedagemDTO = HospedagemDTO.create(hospedagem.get());
        System.out.println("DTO enviado pelo get/id");
        System.out.println(hospedagemDTO);
        return ResponseEntity.ok(hospedagemDTO);
        
    }
    
    @GetMapping("/reservas/{id}")
    @ApiOperation("Obter detalhes de um hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Hospedagem encontrado"),
            @ApiResponse(code  = 404, message  = "Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity getR(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        List<TipoQuartoReserva> listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(reserva);

        HospedagemDTO hospedagemDTO = new HospedagemDTO(reserva.get(), listaQuartos);
        return ResponseEntity.ok(hospedagemDTO);
        
    }


    @PostMapping
    @ApiOperation("Salva um hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Hospedagem salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Hospedagem"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody HospedagemDTO dto) {
        System.out.println("post");
        System.out.println(dto);
        try {
            Hospedagem hospedagem = converter(dto);
            System.out.println("dto recebido do post");
            System.out.println(dto);
            
            List<QuartoHospedagem> quartoHospedagems = new ArrayList<QuartoHospedagem>();
            for (QuartoHospedagemDTOList quartoHospedagemDto : dto.getListaQuartos()) {
                quartoHospedagems.add(converterQuartoHospedagem(quartoHospedagemDto, hospedagem.getId()));
            }
            List<ProdutoSolicitado> produtoSolicitados = new ArrayList<ProdutoSolicitado>();
            if(dto.getProdutoHospedagem()!=null) {
                for (ProdutoSolicitadoDTOList produtoSolicitadoDto : dto.getProdutoHospedagem()) {
                    produtoSolicitados.add(converterProdutoSolicitado(produtoSolicitadoDto, hospedagem.getId()));
                }
            }
            hospedagem = service.salvarFull(hospedagem, quartoHospedagems, produtoSolicitados);
            return new ResponseEntity(hospedagem, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HospedagemDTO dto) {
    if (!service.getHospedagemById(id).isPresent()) {
        return new ResponseEntity("Hospedagem não encontrado", HttpStatus.NOT_FOUND);
    }
    try {
        Hospedagem hospedagem = converter(dto);
        hospedagem.setId(id);

        AvaliacaoHospedagem avaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem();
        avaliacaoHospedagem.setId(service.getHospedagemById(id).get().getAvaliacaoHospedagem().getId());
        avaliacaoHospedagem = avaliacaoHospedagemService.salvar(avaliacaoHospedagem);
        hospedagem.setAvaliacaoHospedagem(avaliacaoHospedagem);

        List<QuartoHospedagem> quartoHospedagems = new ArrayList<QuartoHospedagem>();
        for (QuartoHospedagemDTOList quartoHospedagemDto : dto.getListaQuartos()) {
            quartoHospedagems.add(converterQuartoHospedagem(quartoHospedagemDto, hospedagem.getId()));
            if (quartoHospedagems.size() > 0) {
                QuartoHospedagem lastQuartoHospedagem = quartoHospedagems.get(quartoHospedagems.size() - 1);
                if (quartoHospedagemDto.getTipoQuarto() != lastQuartoHospedagem.getQuarto().getTipoQuarto().getId()) {
                    lastQuartoHospedagem.setQuarto(null);
                }
            }
        }
        List<ProdutoSolicitado> produtoSolicitados = new ArrayList<ProdutoSolicitado>();
        for (ProdutoSolicitadoDTOList produtoSolicitadoDto : dto.getProdutoHospedagem()) {
            produtoSolicitados.add(converterProdutoSolicitado(produtoSolicitadoDto, hospedagem.getId()));
        }
        hospedagem = service.salvarFull(hospedagem, quartoHospedagems, produtoSolicitados);

        return ResponseEntity.ok(hospedagem);
    } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um hospedagem")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Hospedagem excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Hospedagem não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(hospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Hospedagem converter(HospedagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hospedagem hospedagem = modelMapper.map(dto, Hospedagem.class);
        hospedagem.setValorTotalPago(dto.getValorTotal());
        AvaliacaoHospedagem avaliacaohospedagem = modelMapper.map(dto, AvaliacaoHospedagem.class);
        hospedagem.setAvaliacaoHospedagem(avaliacaohospedagem);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                hospedagem.setCliente(null);
            } else {
                hospedagem.setCliente(cliente.get());
            }
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                hospedagem.setHotel(null);
            } else {
                hospedagem.setHotel(hotel.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                hospedagem.setFuncionario(null);
            } else {
                hospedagem.setFuncionario(funcionario.get());
            }
        }
        if (dto.getStatus() != null) {
            Optional<StatusHospedagem> statushospedagem = statushospedagemService.getStatusHospedagemById(dto.getStatus());
            if (!statushospedagem.isPresent()) {
                hospedagem.setStatusHospedagem(null);
            } else {
                hospedagem.setStatusHospedagem(statushospedagem.get());
            }
        }
        if (dto.getIdReserva() != null) {
            Optional<Reserva> reserva = reservaService.getReservaById(dto.getIdReserva());
            if (!reserva.isPresent()) {
                hospedagem.setReserva(null);
            } else {
                hospedagem.setReserva(reserva.get());
            }
        }
        
        return hospedagem;
    }

    public QuartoHospedagem converterQuartoHospedagem(QuartoHospedagemDTOList dto, Long hospedagemId) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoHospedagem quartoHospedagem = modelMapper.map(dto, QuartoHospedagem.class);
        quartoHospedagem.setId(null);
        if (hospedagemId != null) {
            Optional<Hospedagem> hospedagem = service.getHospedagemById(hospedagemId);
            if (!hospedagem.isPresent()) {
                quartoHospedagem.setHospedagem(null);
            } else {
                quartoHospedagem.setHospedagem(hospedagem.get());
            }
        }
        if (dto.getNum() != null) {
            Optional<Quarto> quarto = quartoService.getQuartoById(dto.getNum());
            if (!quarto.isPresent()) {
                quartoHospedagem.setQuarto(null);
            } else {
                quartoHospedagem.setQuarto(quarto.get());
            }
        }
        return quartoHospedagem;
    }

    public ProdutoSolicitado converterProdutoSolicitado(ProdutoSolicitadoDTOList dto, Long hospedagemId) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoSolicitado produtoSolicitado = modelMapper.map(dto, ProdutoSolicitado.class);
        produtoSolicitado.setQuantidade(dto.getQuant());
        produtoSolicitado.setId(null);
        if (hospedagemId != null) {
            Optional<Hospedagem> hospedagem = service.getHospedagemById(hospedagemId);
            if (!hospedagem.isPresent()) {
                produtoSolicitado.setHospedagem(null);
            } else {
                produtoSolicitado.setHospedagem(hospedagem.get());
            }
        }
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                produtoSolicitado.setProduto(null);
            } else {
                produtoSolicitado.setProduto(produto.get());
            }
        }
        return produtoSolicitado;
    }
}