package com.desafio.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Não pode ser vazio")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Email(message= "Email inválido")
	@Column(unique = true, nullable = false)
	private String email;
	
	@NotBlank(message = "Login não pode ser vazio")
	@Column(unique = true, nullable = false)
	private String login;
	
	@NotBlank(message="Senha não pode ser vazia")
	@Column(nullable = false)
	private String senha;
	

}
