package com.desafio.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.demo.Util.JwtUtil;
import com.desafio.demo.dto.LoginRequest;
import com.desafio.demo.model.Usuario;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthController {
	
	private JwtUtil jwtUtil;
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/token")
	public String getToken(@RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            loginRequest.getLogin(), 
	            loginRequest.getSenha()
	        )
	    );

	    Usuario usuario = (Usuario) authentication.getPrincipal(); // ou UserDetails
	    return jwtUtil.generateToken(usuario);
	}

}
