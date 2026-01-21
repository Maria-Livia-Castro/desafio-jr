package com.desafio.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.desafio.demo.exception.UsuarioDuplicadoException;
import com.desafio.demo.exception.UsuarioNaoEncontradoException;
import com.desafio.demo.model.Usuario;
import com.desafio.demo.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService{
	
	private final UsuarioRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = repository.findByLoginIgnoreCase(login);
		if(usuarioOpt.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return usuarioOpt.get();
	}
	
	public Usuario criar(Usuario usuario) {
		Boolean usuarioExiste = repository.existsByEmailIgnoreCaseOrLoginIgnoreCase(usuario.getEmail(), usuario.getLogin());
		if(usuarioExiste) {
			throw new UsuarioDuplicadoException();
		}
		
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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
				throw new UsuarioDuplicadoException();
			}
		}
		
		usuario.setNome(usuarioAtualizado.getNome());
		usuario.setEmail(usuarioAtualizado.getEmail());
		usuario.setLogin(usuarioAtualizado.getLogin());
		usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
		
		return repository.save(usuario);

	}
	
	public void deletar(Integer id) {
	    Usuario usuario = buscarPorId(id);
	    repository.delete(usuario);
	}
}