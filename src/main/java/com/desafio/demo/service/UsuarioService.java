package com.desafio.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.desafio.demo.exception.UsuarioDuplicadoException;
import com.desafio.demo.exception.UsuarioNaoEncontradoException;
import com.desafio.demo.model.Usuario;
import com.desafio.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository=repository;
	}
	
	public Usuario criar(Usuario usuario) {
		Boolean usuarioExiste = repository.existsByEmailIgnoreCaseOrLoginIgnoreCase(usuario.getEmail(), usuario.getLogin());
		if(usuarioExiste) {
			throw new UsuarioDuplicadoException("Email ou Login já cadastrados");
		}
		
		return repository.save(usuario);
	}
	
	public List<Usuario> listarTodos() {
		return repository.findAll();
	}
	
	public Usuario buscarPorId(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	public Usuario atualizar(Integer id, Usuario usuarioAtualizado) {
		Usuario usuario = buscarPorId(id);
		
		if(!usuario.getEmail().equals(usuarioAtualizado.getEmail()) || !usuario.getLogin().equals(usuarioAtualizado.getLogin())) {
			Boolean usuarioExiste = repository.existsByEmailIgnoreCaseOrLoginIgnoreCase(usuarioAtualizado.getEmail(), usuarioAtualizado.getLogin());
			if(usuarioExiste) {
				throw new UsuarioDuplicadoException("Email ou Login já cadastrados");
			}
		}
		
		usuario.setNome(usuarioAtualizado.getNome());
		usuario.setEmail(usuarioAtualizado.getEmail());
		usuario.setLogin(usuarioAtualizado.getLogin());
		usuario.setSenha(usuarioAtualizado.getSenha());
		
		return repository.save(usuario);

	}
	
	public void deletar(Integer id) {
	    Usuario usuario = buscarPorId(id);
	    repository.delete(usuario);
	}
		

}
