package com.desafio.demo.exception;

public class UsuarioDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioDuplicadoException() {
        super("Email ou Login já cadastrados");
    }
	
	public UsuarioDuplicadoException(String mensagem) {
        super(mensagem);
    }
}