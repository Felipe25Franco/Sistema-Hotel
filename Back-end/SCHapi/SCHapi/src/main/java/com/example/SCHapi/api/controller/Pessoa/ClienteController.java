package com.example.SCHapi.api.controller.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SCHapi.api.dto.Pessoa.ClienteDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Endereco;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Pessoa.Pais;
import com.example.SCHapi.model.entity.Pessoa.Uf;
import com.example.SCHapi.service.Pessoa.ClienteService;
import com.example.SCHapi.service.Pessoa.EnderecoService;
import com.example.SCHapi.service.Pessoa.PaisService;
import com.example.SCHapi.service.Pessoa.UfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@CrossOrigin
public class ClienteController {

    private final ClienteService service;
    private final EnderecoService enderecoService;
    private final UfService ufService;
    private final PaisService paisService;

    @GetMapping()
    public ResponseEntity get() {
       List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            Endereco endereco = cliente.getEndereco();
            endereco.setId(service.getClienteById(id).get().getEndereco().getId());
            endereco = enderecoService.salvar(endereco);
            cliente.setEndereco(endereco);
            System.out.println(dto);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        cliente.setEndereco(endereco);
        cliente.setTelefone1(dto.getDdi1().concat(dto.getDdd1()).concat(dto.getNum1()));
        cliente.setTelefone2(dto.getDdi2().concat(dto.getDdd2()).concat(dto.getNum2()));
        if (dto.getIdUf() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            if (!uf.isPresent()) {
                endereco.setUf(null);
            } else {
                endereco.setUf(uf.get());
            }
        }
        if (dto.getIdPais() != null) {
            Optional<Uf> uf = ufService.getUfById(dto.getIdUf());
            Optional<Pais> pais = paisService.getPaisById(dto.getIdPais());
            if (!pais.isPresent()) {
                uf.get().setPais(null);
            } else {
                uf.get().setPais(pais.get());
            }
        }
        return cliente;
    }
}
