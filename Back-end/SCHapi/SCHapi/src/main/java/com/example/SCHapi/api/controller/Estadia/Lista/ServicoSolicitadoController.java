package com.example.SCHapi.api.controller.Estadia.Lista;

import com.example.SCHapi.api.dto.Estadia.HospedagemDTO;
import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.RelacaoHorarioServicoDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.RelacaoHorarioServicoDTOList2;
import com.example.SCHapi.api.dto.Estadia.Lista.ServicoSolicitadoDTO;
import com.example.SCHapi.api.dto.Estadia.Lista.TipoQuartoReservaDTOList;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.RelacaoHorarioServico;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.RelacaoHorarioServicoService;
import com.example.SCHapi.service.Estadia.Lista.ServicoSolicitadoService;
import com.example.SCHapi.service.Servico.HorarioServicoService;
import com.example.SCHapi.service.Servico.ServicoService;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servicoSolicitados")
@RequiredArgsConstructor
@Api("API de Serviço Solicitado")
@CrossOrigin
public class ServicoSolicitadoController {

    private final ServicoSolicitadoService service;
    private final HospedagemService hospedagemService;
    private final ServicoService servicoService;
    private final HorarioServicoService horarioServicoService;
    private final RelacaoHorarioServicoService relacaoHorarioServicoService;

    @GetMapping()
    @ApiOperation("Obter a lista de serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Serviço Solicitado retornada com sucesso"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor"),
            @ApiResponse(code  = 404, message  = "Serviço Solicitado não encontrado")
    })
    public ResponseEntity get() {
       List<ServicoSolicitado> servicoSolicitados = service.getServicoSolicitados();
        return ResponseEntity.ok(servicoSolicitados.stream().map(ServicoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serviço Solicitado encontrado"),
            @ApiResponse(code  = 404, message  = "Serviço Solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ServicoSolicitado> servicoSolicitado = service.getServicoSolicitadoById(id);
        if (!servicoSolicitado.isPresent()) {
            return new ResponseEntity("ServicoSolicitado não encontrada", HttpStatus.NOT_FOUND);
        }
        ServicoSolicitadoDTO servicoSolicitadoDTO = new ServicoSolicitadoDTO();
        servicoSolicitadoDTO = ServicoSolicitadoDTO.create(servicoSolicitado.get());
        return ResponseEntity.ok(servicoSolicitadoDTO);
        
    }

    @GetMapping("/hospedagens/{id}")
    @ApiOperation("Obter detalhes de um serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serviço Solicitado encontrado"),
            @ApiResponse(code  = 404, message  = "Serviço Solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity getByHospedagemId(@PathVariable("id") Long id) {
        
        Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem  não encontrada", HttpStatus.NOT_FOUND);
        }
        List<ServicoSolicitado> servicoSolicitados = service.getServicoSolicitadoByHospedagem(hospedagem);
        return ResponseEntity.ok(servicoSolicitados.stream().map(ServicoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation("Salva um serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Serviço Solicitado salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Serviço Solicitado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody ServicoSolicitadoDTO dto) {
        System.out.println("post dto");
        System.out.println(dto);
        try {
            ServicoSolicitado servicoSolicitado = converter(dto);
            
            List<RelacaoHorarioServico> relacaoHorarioServicos = new ArrayList<RelacaoHorarioServico>();
            for (RelacaoHorarioServicoDTOList2 relacaoHorarioServicoDto2 : dto.getRelacaoHorarioServico()) {
                if (relacaoHorarioServicoDto2.getSelect() != null)
                     if (relacaoHorarioServicoDto2.getSelect())
                        relacaoHorarioServicos.add(converterRelacaoHorarioServico2(relacaoHorarioServicoDto2, servicoSolicitado.getId()));
            }
            servicoSolicitado = service.salvarFull(servicoSolicitado, relacaoHorarioServicos);
            return new ResponseEntity(servicoSolicitado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serviço Solicitado alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Serviço Solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ServicoSolicitadoDTO dto) {
            System.out.println("put dto");
            System.out.println(dto);
        if (!service.getServicoSolicitadoById(id).isPresent()) {
            return new ResponseEntity("ServicoSolicitado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ServicoSolicitado servicoSolicitado = converter(dto);
            servicoSolicitado.setId(id);
            
            List<RelacaoHorarioServico> relacaoHorarioServicos = new ArrayList<RelacaoHorarioServico>();
            for (RelacaoHorarioServicoDTOList2 relacaoHorarioServicoDto2 : dto.getRelacaoHorarioServico()) {
                if (relacaoHorarioServicoDto2.getSelect() != null)
                    if (relacaoHorarioServicoDto2.getSelect())
                        relacaoHorarioServicos.add(converterRelacaoHorarioServico2(relacaoHorarioServicoDto2, servicoSolicitado.getId()));
            
            }
            
            service.salvarFull(servicoSolicitado, relacaoHorarioServicos);
            return ResponseEntity.ok(servicoSolicitado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um serviço solicitado")
    @ApiResponses({
            @ApiResponse(code  = 204, message  = "Serviço Solicitado excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Serviço Solicitado não encontrado"),
            @ApiResponse(code  = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ServicoSolicitado> servicoSolicitado = service.getServicoSolicitadoById(id);
        if (!servicoSolicitado.isPresent()) {
            return new ResponseEntity("ServicoSolicitado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(servicoSolicitado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ServicoSolicitado converter(ServicoSolicitadoDTO dto) {
        
        ServicoSolicitado servicoSolicitado = new ServicoSolicitado();
        servicoSolicitado.setId(dto.getId());
        servicoSolicitado.setValorTotal(dto.getValorTotal());
        if (dto.getIdHospedagem() != null) {
            Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(dto.getIdHospedagem());
            if (!hospedagem.isPresent()) {
                servicoSolicitado.setHospedagem(null);
            } else {
                servicoSolicitado.setHospedagem(hospedagem.get());
            }
        }
        if (dto.getIdServico() != null) {
            Optional<Servico> servico = servicoService.getServicoById(dto.getIdServico());
            if (!servico.isPresent()) {
                servicoSolicitado.setServico(null);
            } else {
                servicoSolicitado.setServico(servico.get());
            }
        }
        return servicoSolicitado;
    }

    public RelacaoHorarioServico converterRelacaoHorarioServico(RelacaoHorarioServicoDTOList dto, Long servicoSolicitadoId) {
        ModelMapper modelMapper = new ModelMapper();
        RelacaoHorarioServico relacaoHorarioServico = modelMapper.map(dto, RelacaoHorarioServico.class);
        
        if (servicoSolicitadoId != null) {
            Optional<ServicoSolicitado> servicoSolicitado = service.getServicoSolicitadoById(servicoSolicitadoId);
            if (!servicoSolicitado.isPresent()) {
                relacaoHorarioServico.setServicoSolicitado(null);
            } else {
                relacaoHorarioServico.setServicoSolicitado(servicoSolicitado.get());
            }
        }
        if (dto.getIdHorarioServico() != null) {
            Optional<HorarioServico> horarioServico = horarioServicoService.getHorarioServicoById(dto.getIdHorarioServico());
            if (!horarioServico.isPresent()) {
                relacaoHorarioServico.setHorarioServico(null);
            } else {
                relacaoHorarioServico.setHorarioServico(horarioServico.get());
            }
        }
        return relacaoHorarioServico;
    }

    public RelacaoHorarioServico converterRelacaoHorarioServico2(RelacaoHorarioServicoDTOList2 dto, Long servicoSolicitadoId) {
        
        RelacaoHorarioServico relacaoHorarioServico = new RelacaoHorarioServico();
        relacaoHorarioServico.setQuantidadeVagas(dto.getQuantidadeVagas());
        
        System.out.println("id servicosolicitado");
        System.out.println(servicoSolicitadoId);
        if (servicoSolicitadoId != null) {
            Optional<ServicoSolicitado> servicoSolicitado = service.getServicoSolicitadoById(servicoSolicitadoId);
            if (!servicoSolicitado.isPresent()) {
                relacaoHorarioServico.setServicoSolicitado(null);
            } else {
                relacaoHorarioServico.setServicoSolicitado(servicoSolicitado.get());
                
            }
        }
        if (dto.getIdHorarioServico() != null) {
            Optional<HorarioServico> horarioServico = horarioServicoService.getHorarioServicoById(dto.getIdHorarioServico());
            if (!horarioServico.isPresent()) {
                relacaoHorarioServico.setHorarioServico(null);
            } else {
                relacaoHorarioServico.setHorarioServico(horarioServico.get());
                
            }
        }
        System.out.println("\napos converter o dto do shoraris");
        
        return relacaoHorarioServico;
    }
}