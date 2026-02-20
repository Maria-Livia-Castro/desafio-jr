package com.desafio.demo.service;

import java.util.List;
import java.util.Optional;

import com.desafio.demo.dto.UsuarioRequest;
import com.desafio.demo.dto.UsuarioResponse;
import com.desafio.demo.mapper.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;

    @Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = repository.findByLoginIgnoreCase(login);
		if(usuarioOpt.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return usuarioOpt.get();
	}
	
	public UsuarioResponse criar(UsuarioRequest request) {
		Boolean usuarioExiste = repository.existsByEmailIgnoreCaseOrLoginIgnoreCase(request.getEmail(), request.getLogin());
		if(usuarioExiste) {
			throw new UsuarioDuplicadoException();
		}

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario salvo = repository.save(usuario);
        return usuarioMapper.toResponse(salvo);

    }
	
	public List<UsuarioResponse> listarTodos() {
		return repository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
	}

    public UsuarioResponse buscarPorId(Integer id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        return usuarioMapper.toResponse(usuario);
    }
	
	public UsuarioResponse atualizar(Integer id, UsuarioRequest request) {
		Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        if(!usuario.getEmail().equals(request.getEmail()) || !usuario.getLogin().equals(request.getLogin())) {
			Boolean usuarioExiste = repository.existsByEmailIgnoreCaseOrLoginIgnoreCase(request.getEmail(), request.getLogin());
			if(usuarioExiste) {
				throw new UsuarioDuplicadoException();
			}
		}

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setLogin(request.getLogin());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        Usuario atualizado = repository.save(usuario);
        return usuarioMapper.toResponse(atualizado);

    }
	
	public void deletar(Integer id) {
	    Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        repository.delete(usuario);
	}
}