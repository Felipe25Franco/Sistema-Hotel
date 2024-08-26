package com.example.SCHapi.api.controller.Estadia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.api.dto.Estadia.ReservaDTO;
import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTOList;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Estadia.StatusReserva;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Funcionario;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.service.Estadia.ReservaService;
import com.example.SCHapi.service.Estadia.StatusReservaService;
import com.example.SCHapi.service.Estadia.Lista.TipoQuartoReservaService;
import com.example.SCHapi.service.Pessoa.ClienteService;
import com.example.SCHapi.service.Pessoa.FuncionarioService;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Quarto.TipoQuartoService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin
public class ReservaController {
    private final ReservaService service;
    private final ClienteService clienteService;
    private final HotelService hotelService;
    private final FuncionarioService funcionarioService;
    private final StatusReservaService statusreservaService;
    private final TipoQuartoReservaService tipoQuartoReservaService;
    private final TipoQuartoService tipoQuartoService;

    @GetMapping()
    // @Operation(summary ="Obter a lista de reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Lista de Reserva retornada com sucesso"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")//,
    //         //@ApiResponse(responseCode  = "404", description  = "Reserva não encontrado")
    // })
    public ResponseEntity get() {
       List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    } // essa aqui ainda tem q adaptar, mas acho q nao vai influenciar por enquanto

    @GetMapping("/{id}")
    // @Operation(summary ="Obter detalhes de um reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Reserva encontrado"),
    //         @ApiResponse(responseCode  = "404", description  = "Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        //Optional<List<TipoQuartoReserva>> = Optional.of(listaQuartos);
        // !!!!!!!!!!!!!!!!!!!!!!! antes att reverso abaixo
        //reservaDTO = ReservaDTO.create(reserva.get(), listaQuartos);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        List<TipoQuartoReserva> listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(reserva);
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO = ReservaDTO.create(reserva.get());
        System.out.println("get dto");
        System.out.println(reservaDTO);
        return ResponseEntity.ok(reservaDTO);
        // return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @PostMapping
    // @Operation(summary ="Salva um reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "201", description  = "Reserva salvo com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Erro ao salvar o Reserva"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity post(@RequestBody ReservaDTO dto) {
        try {
            Reserva reserva = converter(dto);
            // loop para cada elemento da lista salvar o tipoquartoreserva
            // for (TipoQuartoReservaDTOList tipoQuartoReservaDto : dto.getListaQuartos()) {
            //     TipoQuartoReserva tipoQuartoReserva = converterTipoQuartoReserva(tipoQuartoReservaDto, reserva.getId());
            //     tipoQuartoReservaService.salvar(tipoQuartoReserva);
            // }
            List<TipoQuartoReserva> tipoQuartoReservas = new ArrayList<TipoQuartoReserva>();
            for (TipoQuartoReservaDTOList tipoQuartoReservaDto : dto.getListaQuartos()) {
                tipoQuartoReservas.add(converterTipoQuartoReserva(tipoQuartoReservaDto, reserva.getId()));
            }
            reserva = service.salvarFull(reserva, tipoQuartoReservas);
            return new ResponseEntity(reserva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    // @Operation(summary ="Atualiza um reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "200", description  = "Reserva alterado com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ReservaDTO dto) {
        if (!service.getReservaById(id).isPresent()) {
            return new ResponseEntity("Reserva não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Reserva reserva = converter(dto);
            reserva.setId(id);
            //converter lista de tipoquartoresrva
            List<TipoQuartoReserva> tipoQuartoReservas = new ArrayList<TipoQuartoReserva>();
            for (TipoQuartoReservaDTOList tipoQuartoReservaDto : dto.getListaQuartos()) {
                tipoQuartoReservas.add(converterTipoQuartoReserva(tipoQuartoReservaDto, reserva.getId()));
            }
            service.salvarFull(reserva, tipoQuartoReservas);
            return ResponseEntity.ok(reserva);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    // @Operation(summary ="Exclui um reserva")
    // @ApiResponses({
    //         @ApiResponse(responseCode  = "204", description  = "Reserva excluído com sucesso"),
    //         @ApiResponse(responseCode  = "404", description  = "Reserva não encontrado"),
    //         @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    // })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(reserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Reserva converter(ReservaDTO dto) {
        System.out.println(dto);
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                reserva.setCliente(null);
            } else {
                reserva.setCliente(cliente.get());
            }
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                reserva.setHotel(null);
            } else {
                reserva.setHotel(hotel.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                reserva.setFuncionario(null);
            } else {
                reserva.setFuncionario(funcionario.get());
            }
        }
        if (dto.getStatus() != null) {
            Optional<StatusReserva> statusReserva = statusreservaService.getStatusReservaById(dto.getStatus());
            if (!statusReserva.isPresent()) {
                reserva.setStatusReserva(null);
            } else {
                reserva.setStatusReserva(statusReserva.get());
            }
        }
        return reserva;
    }

    public TipoQuartoReserva converterTipoQuartoReserva(TipoQuartoReservaDTOList dto, Long reservaId) {
        ModelMapper modelMapper = new ModelMapper();
        TipoQuartoReserva tipoQuartoReserva = modelMapper.map(dto, TipoQuartoReserva.class);
        tipoQuartoReserva.setId(null);
        if (reservaId != null) {
            Optional<Reserva> reserva = service.getReservaById(reservaId);
            if (!reserva.isPresent()) {
                tipoQuartoReserva.setReserva(null);
            } else {
                tipoQuartoReserva.setReserva(reserva.get());
            }
        }
        if (dto.getTipoQuarto() != null) {
            Optional<TipoQuarto> tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getTipoQuarto());
            if (!tipoQuarto.isPresent()) {
                tipoQuartoReserva.setTipoQuarto(null);
            } else {
                tipoQuartoReserva.setTipoQuarto(tipoQuarto.get());
            }
        }
        return tipoQuartoReserva;
    }
}