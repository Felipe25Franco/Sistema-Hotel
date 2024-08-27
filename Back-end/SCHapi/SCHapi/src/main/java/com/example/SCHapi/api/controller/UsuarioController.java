package com.example.SCHapi.api.controller;


import lombok.RequiredArgsConstructor;
import io.swagger.annotations.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.SCHapi.api.dto.UsuarioDTO;
import com.example.SCHapi.api.dto.Produto.TipoProdutoDTO;
import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Usuario;
import com.example.SCHapi.model.entity.Produto.TipoProduto;
// import com.example.SCHapi.security.JwtService;
import com.example.SCHapi.service.UsuarioService;


@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Api("API de usuarios")
@CrossOrigin

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    // private final JwtService jwtService;

    private final UsuarioService service;

    @GetMapping()
    @ApiOperation("Obter a lista de usuarios")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de usuarios retornada com sucesso"),
            @ApiResponse(code  = 404, message  = "Usuario não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get() {
       List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um usuario")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Usuario encontrado"),
            @ApiResponse(code  = 404, message  = "Usuario não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um usuario")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Usuario salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o usuario"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody UsuarioDTO dto) {
        try {
            
            Usuario usuario = converter(dto);
            usuario = service.salvar(usuario);
            String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
            usuario.setSenha(senhaCriptografada);
            System.err.println(dto);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Usuario")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Usuario alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Usuario não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        if (!service.getUsuarioById(id).isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            
            Usuario usuario = converter(dto);
            service.salvar(usuario);
            String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
            usuario.setSenha(senhaCriptografada);
            usuario.setId(id);
            System.out.println(dto);
            service.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Usuario")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Usuario excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Usuario não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(usuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }
}
