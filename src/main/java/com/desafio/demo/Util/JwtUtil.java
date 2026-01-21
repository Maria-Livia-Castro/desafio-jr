package com.desafio.demo.Util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.desafio.demo.model.Usuario;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String KEY = "ocicsmauelMprX3GjYjx9vo0C7JzOpB0";
	
	private final SecretKey secretKey;
	
	public JwtUtil() {
		secretKey = Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(Usuario usuario) {
	    return Jwts.builder()
	            .subject(usuario.getLogin())
	            .issuedAt(new Date())
	            .expiration(new Date(new Date().getTime() + 3600 * 1000))
	            .signWith(secretKey)
	            .compact();
	}

	public String getLoginFromToken(String jwt) {
	    return Jwts.parser()
	            .verifyWith(secretKey)
	            .build()
	            .parseSignedClaims(jwt)
	            .getPayload()
	            .getSubject();
	}

	public Boolean validateToken(String jwt) {
	    try {
	        Jwts.parser()
	            .verifyWith(secretKey)
	            .build()
	            .parseSignedClaims(jwt);
	        return true;
	    } catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	}

}
