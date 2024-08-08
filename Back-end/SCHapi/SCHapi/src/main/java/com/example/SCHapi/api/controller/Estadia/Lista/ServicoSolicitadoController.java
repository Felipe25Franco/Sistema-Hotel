package com.example.SCHapi.api.controller.Estadia.Lista;

import com.example.SCHapi.api.dto.Estadia.HospedagemDTO;
import com.example.SCHapi.api.dto.Estadia.Lista.ProdutoSolicitadoDTOList;
import com.example.SCHapi.api.dto.Estadia.Lista.ServicoSolicitadoDTO;
import com.example.SCHapi.api.dto.Servico.Lista.RelacaoHorarioServicoDTOList;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Servico.HorarioServico;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.entity.Servico.Lista.RelacaoHorarioServico;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.ServicoSolicitadoService;
import com.example.SCHapi.service.Servico.HorarioServicoService;
import com.example.SCHapi.service.Servico.ServicoService;
import com.example.SCHapi.service.Servico.Lista.RelacaoHorarioServicoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servicoSolicitados")
@RequiredArgsConstructor
@CrossOrigin
public class ServicoSolicitadoController {

    private final ServicoSolicitadoService service;
    private final HospedagemService hospedagemService;
    private final ServicoService servicoService;
    private final HorarioServicoService horarioServicoService;
    private final RelacaoHorarioServicoService relacaoHorarioServicoService;

    @GetMapping()
    public ResponseEntity get() {
       List<ServicoSolicitado> servicoSolicitados = service.getServicoSolicitados();
        return ResponseEntity.ok(servicoSolicitados.stream().map(ServicoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ServicoSolicitado> servicoSolicitado = service.getServicoSolicitadoById(id);
        if (!servicoSolicitado.isPresent()) {
            return new ResponseEntity("ServicoSolicitado não encontrada", HttpStatus.NOT_FOUND);
        }
        ServicoSolicitadoDTO servicoSolicitadoDTO = new ServicoSolicitadoDTO();
        servicoSolicitadoDTO = ServicoSolicitadoDTO.create(servicoSolicitado.get());
        return ResponseEntity.ok(servicoSolicitadoDTO);
        //return ResponseEntity.ok(servicoSolicitado.map(ServicoSolicitadoDTO::create));
    }

    @GetMapping("/hospedagens/{id}")//retorna os servicos solidictados da hospedagem do id
    public ResponseEntity getByHospedagemId(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hospedagem  não encontrada", HttpStatus.NOT_FOUND);
        }
        List<ServicoSolicitado> servicoSolicitados = service.getServicoSolicitadoByHospedagem(hospedagem);
        return ResponseEntity.ok(servicoSolicitados.stream().map(ServicoSolicitadoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ServicoSolicitadoDTO dto) {
        try {
            ServicoSolicitado servicoSolicitado = converter(dto);
            servicoSolicitado = service.salvar(servicoSolicitado);
            // loop para cada elemento da lista salvar o produtosolicitado
            for (RelacaoHorarioServicoDTOList relacaoHorarioServicoDto : dto.getRelacaoHorarioServico()) {
                RelacaoHorarioServico relacaoHorarioServico = converterRelacaoHorarioServico(relacaoHorarioServicoDto, servicoSolicitado.getId());
                relacaoHorarioServicoService.salvar(relacaoHorarioServico);
            }
            return new ResponseEntity(servicoSolicitado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ServicoSolicitadoDTO dto) {
        if (!service.getServicoSolicitadoById(id).isPresent()) {
            return new ResponseEntity("ServicoSolicitado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ServicoSolicitado servicoSolicitado = converter(dto);
            servicoSolicitado.setId(id);
            //System.out.println(dto);
            for (RelacaoHorarioServico relacaoHorarioServico : service.getServicoSolicitadoById(id).get().getRelacaoHorarioServico()){
                relacaoHorarioServicoService.excluir(relacaoHorarioServico);
            }
            service.salvar(servicoSolicitado);
            for (RelacaoHorarioServicoDTOList relacaoHorarioServicoDto : dto.getRelacaoHorarioServico()) {
                RelacaoHorarioServico relacaoHorarioServico = converterRelacaoHorarioServico(relacaoHorarioServicoDto, servicoSolicitado.getId());
                relacaoHorarioServicoService.salvar(relacaoHorarioServico);
            }
            return ResponseEntity.ok(servicoSolicitado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
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
        ModelMapper modelMapper = new ModelMapper();
        ServicoSolicitado servicoSolicitado = modelMapper.map(dto, ServicoSolicitado.class);
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
        //relacaoHorarioServico.setQuantidade(dto.getQuant());
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
}