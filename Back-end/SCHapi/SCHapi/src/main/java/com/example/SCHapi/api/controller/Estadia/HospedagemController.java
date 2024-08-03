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

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospedagens")
@RequiredArgsConstructor
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
    public ResponseEntity get() {
       List<Hospedagem> hospedagens = service.getHospedagens();
        return ResponseEntity.ok(hospedagens.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity get(@PathVariable("id") Long id) {
    //     Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
    //     if (!hospedagem.isPresent()) {
    //         return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
    //     }
    //     return ResponseEntity.ok(hospedagem.map(HospedagemDTO::create));
    // }
    
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        //Optional<List<QuartoHospedagem>> = Optional.of(listaQuartos);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        List<QuartoHospedagem> listaQuartos = quartoHospedagemService.getQuartoHospedagemByHospedagem(hospedagem);
        List<ProdutoSolicitado> produtoSolicitado = produtoSolicitadoService.getProdutoSolicitadoByHospedagem(hospedagem);
        HospedagemDTO hospedagemDTO = new HospedagemDTO();
        hospedagemDTO = HospedagemDTO.create(hospedagem.get());
        return ResponseEntity.ok(hospedagemDTO);
        // return ResponseEntity.ok(hospedagem.map(HospedagemDTO::create));
    }
    
    @GetMapping("/reservas/{id}")
    public ResponseEntity getR(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        List<TipoQuartoReserva> listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(reserva);

        HospedagemDTO hospedagemDTO = new HospedagemDTO(reserva.get(), listaQuartos);
        return ResponseEntity.ok(hospedagemDTO);
        // return ResponseEntity.ok(hospedagem.map(HospedagemDTO::create));
    }


    @PostMapping
    public ResponseEntity post(@RequestBody HospedagemDTO dto) {
        try {
            Hospedagem hospedagem = converter(dto);
            hospedagem = service.salvar(hospedagem);
            // loop para cada elemento da lista salvar o quartoHospedagem
            List<Long>  idTipoQuartoAvaliado = new ArrayList<Long>(); //armazenar tipoquartos ja feito pra avaliacao
            for (QuartoHospedagemDTOList quartoHospedagemDto : dto.getListaQuartos()) {
                QuartoHospedagem quartoHospedagem = converterQuartoHospedagem(quartoHospedagemDto, hospedagem.getId());
                quartoHospedagemService.salvar(quartoHospedagem);
                //criar uma avaliacao pra cada tipo quarto
                if(!idTipoQuartoAvaliado.contains(quartoHospedagem.getQuarto().getTipoQuarto().getId())){
                    idTipoQuartoAvaliado.add(quartoHospedagem.getQuarto().getTipoQuarto().getId());
                    AvaliacaoQuarto avaliacaoQuarto = new AvaliacaoQuarto();
                    avaliacaoQuarto.setTipoQuarto(quartoHospedagem.getQuarto().getTipoQuarto());
                    avaliacaoQuarto.setHospedagem(hospedagem);
                    avaliacaoQuarto.setNota((float) -1);
                    avaliacaoQuarto = avaliacaoQuartoService.salvarSemValidar(avaliacaoQuarto);
                    //System.out.println(quartoHospedagem.getId());
                }
            }
            // loop para cada elemento da lista salvar o produtosolicitado
            for (ProdutoSolicitadoDTOList produtoSolicitadoDto : dto.getProdutoHospedagem()) {
                ProdutoSolicitado produtoSolicitado = converterProdutoSolicitado(produtoSolicitadoDto, hospedagem.getId());
                produtoSolicitadoService.salvar(produtoSolicitado);
            }
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
            //avaliacao hospedagem
            AvaliacaoHospedagem avaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem();
            avaliacaoHospedagem.setId(service.getHospedagemById(id).get().getAvaliacaoHospedagem().getId());
            avaliacaoHospedagem = avaliacaoHospedagemService.salvar(avaliacaoHospedagem);
            hospedagem.setAvaliacaoHospedagem(avaliacaoHospedagem);
            // AvaliacaoHospedagem avaliacaoHospedagem = hospedagem.getAvaliacaoHospedagem();
            // if(avaliacaoHospedagem != null) {
            //     avaliacaoHospedagem.setId(service.getHospedagemById(id).get().getAvaliacaoHospedagem().getId());
            //     avaliacaoHospedagem = avaliacaoHospedagemService.salvar(avaliacaoHospedagem);
            //     hospedagem.setAvaliacaoHospedagem(avaliacaoHospedagem);
            // }
            // else {
            //     avaliacaoHospedagem = avaliacaoHospedagemService.salvar(avaliacaoHospedagem);
            //     hospedagem.setAvaliacaoHospedagem(avaliacaoHospedagem);
            // }
            // excluir a lista antiga de TipoQUarto
            for (QuartoHospedagem quartoHospedagem : service.getHospedagemById(id).get().getQuartoHospedagem()){
                quartoHospedagemService.excluir(quartoHospedagem);
            }
            for (ProdutoSolicitado produtoSolicitado : service.getHospedagemById(id).get().getProdutoSolicitado()){
                produtoSolicitadoService.excluir(produtoSolicitado);
            }
            service.salvar(hospedagem);
            // Salvar os novos TipoQUarto
            for (QuartoHospedagemDTOList quartoHospedagemDto : dto.getListaQuartos()) {
                QuartoHospedagem quartoHospedagem = converterQuartoHospedagem(quartoHospedagemDto, hospedagem.getId());
                quartoHospedagemService.salvar(quartoHospedagem);
            }
            for (ProdutoSolicitadoDTOList produtoSolicitadoDto : dto.getProdutoHospedagem()) {
                ProdutoSolicitado produtoSolicitado = converterProdutoSolicitado(produtoSolicitadoDto, hospedagem.getId());
                produtoSolicitadoService.salvar(produtoSolicitado);
            }

            //remanejar avaliacoes
            //System.out.println(hospedagem.getId());
            //Hospedagem hospedagemS = service.getHospedagemById(id).get();
            for (QuartoHospedagem quartoHospedagem : quartoHospedagemService.getQuartoHospedagemByHospedagem(service.getHospedagemById(id))){
               Boolean flagBreak = false;
               for (AvaliacaoQuarto avaliacaoQuarto : avaliacaoQuartoService.getAvaliacaoQuartoByHospedagem(service.getHospedagemById(id))){
                    if(avaliacaoQuarto.getTipoQuarto().getId()==quartoHospedagem.getQuarto().getTipoQuarto().getId()){
                        System.out.println("break "+avaliacaoQuarto.getTipoQuarto().getId());
                        flagBreak = true;
                        break;
                    }
                }
                if(!flagBreak){
                    //System.out.println("criei ava topquarto" + quartoHospedagem.getQuarto().getTipoQuarto().getId());
                    AvaliacaoQuarto avaliacaoQuartoNew = new AvaliacaoQuarto();
                    avaliacaoQuartoNew.setNota((float) -1);
                    avaliacaoQuartoNew.setTipoQuarto(quartoHospedagem.getQuarto().getTipoQuarto());
                    avaliacaoQuartoNew.setHospedagem(hospedagem);
                    avaliacaoQuartoService.salvarSemValidar(avaliacaoQuartoNew);
                }
            }
            for (AvaliacaoQuarto avaliacaoQuarto : avaliacaoQuartoService.getAvaliacaoQuartoByHospedagem(service.getHospedagemById(id))){
                Boolean flagBreak = false;
                for (QuartoHospedagem quartoHospedagem: quartoHospedagemService.getQuartoHospedagemByHospedagem(service.getHospedagemById(id))){
                    if(avaliacaoQuarto.getTipoQuarto().getId()==quartoHospedagem.getQuarto().getTipoQuarto().getId()){
                        System.out.println("break "+avaliacaoQuarto.getTipoQuarto().getId());
                        flagBreak = true;
                        break;
                    }
                }
                if(!flagBreak){
                    //System.out.println("deletei ava topquarto" + avaliacaoQuarto.getTipoQuarto().getId());
                    avaliacaoQuartoService.excluir(avaliacaoQuarto);
                }
            }
            return ResponseEntity.ok(hospedagem);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
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
        if (dto.getIdStatusHospedagem() != null) {
            Optional<StatusHospedagem> statushospedagem = statushospedagemService.getStatusHospedagemById(dto.getIdStatusHospedagem());
            if (!statushospedagem.isPresent()) {
                hospedagem.setStatusHospedagem(null);
            } else {
                hospedagem.setStatusHospedagem(statushospedagem.get());
            }
        }
        // if (dto.getIdAvaliacaoHospedagem() != null) {
        //     Optional<AvaliacaoHospedagem> avaliacaohospedagemO = avaliacaoHospedagemService.getAvaliacaoHospedagemById(dto.getIdAvaliacaoHospedagem());
        //     if (!avaliacaohospedagemO.isPresent()) {
        //         hospedagem.setAvaliacaoHospedagem(null);
        //     } else {
        //         hospedagem.setAvaliacaoHospedagem(avaliacaohospedagemO.get());
        //     }
        // }
        return hospedagem;
    }

    public QuartoHospedagem converterQuartoHospedagem(QuartoHospedagemDTOList dto, Long hospedagemId) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoHospedagem quartoHospedagem = modelMapper.map(dto, QuartoHospedagem.class);
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