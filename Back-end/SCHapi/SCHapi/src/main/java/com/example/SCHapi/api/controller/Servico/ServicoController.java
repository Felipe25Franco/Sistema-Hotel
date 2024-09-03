package com.example.SCHapi.api.controller.Servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTOList;
import com.example.SCHapi.api.dto.Servico.HorarioServicoDTO;
import com.example.SCHapi.api.dto.Servico.ServicoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.entity.Servico.StatusServico;
import com.example.SCHapi.model.entity.Servico.TipoServico;
import com.example.SCHapi.service.Pessoa.HotelService;
import com.example.SCHapi.service.Servico.HorarioServicoService;
import com.example.SCHapi.service.Servico.ServicoService;
import com.example.SCHapi.service.Servico.StatusServicoService;
import com.example.SCHapi.service.Servico.TipoServicoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
@Api("API de Serviço")
@CrossOrigin
public class ServicoController {
    private final ServicoService service;
    private final HotelService hotelService;
    private final TipoServicoService tipoServicoService;
    private final StatusServicoService statusServicoService;
    private final HorarioServicoService horarioServicoService;

    
    @GetMapping()
    @ApiOperation("Obter a lista de serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Serviço retornada com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Serviço não encontrado")
    })
    public ResponseEntity get() {
        List<Servico> servicos = service.getServicos();
        
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serviço encontrado"),
            @ApiResponse(code  = 404, message  = "Serviço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Serviço") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return new ResponseEntity("Servico não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um serviço")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Serviço salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Serviço"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody ServicoDTO dto) {
        try {
            Servico servico = converter(dto);
            
            List<HorarioServico> horarioServicos = new ArrayList<HorarioServico>();
            for (HorarioServicoDTO horarioServicoDto : dto.getHorarioServicos()) {
                horarioServicos.add(converterHorarioServico(horarioServicoDto, servico.getId()));
            }
            servico = service.salvarFull(servico, horarioServicos);
            return new ResponseEntity(servico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um serviço")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serviço alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Serviço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ServicoDTO dto) {
        System.out.println(dto);
        if (!service.getServicoById(id).isPresent()) {
            return new ResponseEntity("Servico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Servico servico = converter(dto);
            servico.setId(id);
            
            List<HorarioServico> horarioServicos = new ArrayList<HorarioServico>();
            for (HorarioServicoDTO horarioServicoDto : dto.getHorarioServicos()) {
                horarioServicos.add(converterHorarioServico(horarioServicoDto, servico.getId()));
            }
            service.salvarFull(servico, horarioServicos);
            return ResponseEntity.ok(servico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um serviço")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Serviço excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Serviço não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return new ResponseEntity("Servico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(servico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                servico.setHotel(null);
            } else {
                servico.setHotel(hotel.get());
            }
        }
        if (dto.getIdTipoServico() != null) {
            Optional<TipoServico> tiposervico = tipoServicoService.getTipoServicoById(dto.getIdTipoServico());
            if (!tiposervico.isPresent()) {
                servico.setTipoServico(null);
            } else {
                servico.setTipoServico(tiposervico.get());
            }
        }
        if (dto.getStatus() != null) {
            Optional<StatusServico> statusservico = statusServicoService.getStatusServicoById(dto.getStatus());
            if (!statusservico.isPresent()) {
                servico.setStatusServico(null);
            } else {
                servico.setStatusServico(statusservico.get());
            }
        }
        return servico;
    }

    public HorarioServico converterHorarioServico(HorarioServicoDTO dto, Long servicoId) {
        ModelMapper modelMapper = new ModelMapper();
        HorarioServico horarioServico = modelMapper.map(dto, HorarioServico.class);
        //horarioServico.setQuantidade(dto.getQuant());
        if (servicoId != null) {
            Optional<Servico> servico = service.getServicoById(servicoId);
            if (!servico.isPresent()) {
                horarioServico.setServico(null);
            } else {
                horarioServico.setServico(servico.get());
            }
        }
        return horarioServico;
    }
}