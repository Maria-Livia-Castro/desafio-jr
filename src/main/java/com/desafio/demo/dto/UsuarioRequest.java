package com.desafio.demo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioRequest {
	
	@NotBlank(message="Não pode ser vazio")
	private String nome;
	
	@NotBlank(message="Não pode ser vazio")
	@Email(message= "Email inválido")
	private String email;
	
	@NotBlank(message = "Login não pode ser vazio")
	private String login;
	
	@NotBlank(message="Senha não pode ser vazia")
	@Length(min = 4, message = "Senha deve conter 4 ou mais caracteres")
	private String senha;
}