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
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioRequest.getNome());
		usuario.setEmail(usuarioRequest.getEmail());
		usuario.setLogin(usuarioRequest.getLogin());
		usuario.setSenha(usuarioRequest.getSenha());
		
		usuario = service.criar(usuario);
		
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		usuarioResponse.setId(usuario.getId());
		usuarioResponse.setNome(usuario.getNome());
		usuarioResponse.setEmail(usuario.getEmail());
		usuarioResponse.setLogin(usuario.getLogin());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponse>> listarTodos() {
		List<Usuario> usuarios = service.listarTodos();
		List<UsuarioResponse> usuariosResponse = new ArrayList<UsuarioResponse>();
		for(Usuario usuario: usuarios) {
			UsuarioResponse usuarioResponse = new UsuarioResponse();
			usuarioResponse.setId(usuario.getId());
			usuarioResponse.setNome(usuario.getNome());
			usuarioResponse.setEmail(usuario.getEmail());
			usuarioResponse.setLogin(usuario.getLogin());
			usuariosResponse.add(usuarioResponse);
		}
		
		return ResponseEntity.ok().body(usuariosResponse);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id){
		Usuario usuario = service.buscarPorId(id);
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		usuarioResponse.setId(usuario.getId());
		usuarioResponse.setNome(usuario.getNome());
		usuarioResponse.setEmail(usuario.getEmail());
		usuarioResponse.setLogin(usuario.getLogin());
		
		return ResponseEntity.ok().body(usuarioResponse);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest usuarioRequest){
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioRequest.getNome());
		usuario.setEmail(usuarioRequest.getEmail());
		usuario.setLogin(usuarioRequest.getLogin());
		usuario.setSenha(usuarioRequest.getSenha());
		
		usuario = service.atualizar(id, usuario);
		
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		usuarioResponse.setId(usuario.getId());
		usuarioResponse.setNome(usuario.getNome());
		usuarioResponse.setEmail(usuario.getEmail());
		usuarioResponse.setLogin(usuario.getLogin());
		
		return ResponseEntity.ok().body(usuarioResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
}
