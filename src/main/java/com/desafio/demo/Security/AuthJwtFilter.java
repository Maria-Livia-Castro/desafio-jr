package com.desafio.demo.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.demo.Util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEABER = "Bearer ";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
	    String jwt = extrairTokenRequest(request);
	    if (jwt != null && jwtUtil.validateToken(jwt)) {
	        String login = jwtUtil.getLoginFromToken(jwt);
	        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
	        UsernamePasswordAuthenticationToken authentication =
	                new UsernamePasswordAuthenticationToken(
	                        userDetails,
	                        null,
	                        userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }

	    filterChain.doFilter(request, response);
	}
	
	private String extrairTokenRequest(HttpServletRequest request) {
		String jwt = request.getHeader(AUTHORIZATION);
		if(jwt == null || !jwt.startsWith(BEABER)) {
			return null;
		}
		
		return jwt.substring(BEABER.length());
		
	}

}
