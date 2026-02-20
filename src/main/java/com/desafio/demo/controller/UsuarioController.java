package com.desafio.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.demo.dto.UsuarioRequest;
import com.desafio.demo.dto.UsuarioResponse;
import com.desafio.demo.model.Usuario;
import com.desafio.demo.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private final UsuarioService service;
	
	public UsuarioController(UsuarioService service) {
		this.service=service;
	}
	
	@PostMapping("/novo")
	public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = service.criar(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }
	
	@GetMapping
	@SecurityRequirement(name = "authJwt")
	public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuariosResponse = service.listarTodos();
        return ResponseEntity.ok().body(usuariosResponse);
    }
	
	@GetMapping("/{id}")
	@SecurityRequirement(name = "authJwt")
	public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id){
        UsuarioResponse usuarioResponse = service.buscarPorId(id);
        return ResponseEntity.ok().body(usuarioResponse);
    }
	
	@PutMapping("/{id}")
	@SecurityRequirement(name = "authJwt")
	public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest usuarioRequest){
        UsuarioResponse usuarioResponse = service.atualizar(id, usuarioRequest);
        return ResponseEntity.ok().body(usuarioResponse);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
}
