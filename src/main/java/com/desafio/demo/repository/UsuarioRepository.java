package com.desafio.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByLoginIgnoreCase(String login);
	Boolean existsByEmailIgnoreCaseOrLoginIgnoreCase(String email, String login);
}