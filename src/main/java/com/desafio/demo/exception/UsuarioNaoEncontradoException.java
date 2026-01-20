package com.desafio.demo.exception;

public class UsuarioNaoEncontradoException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(Integer id) {
		super("Usuário não encontrado: id " + id);
	}
}