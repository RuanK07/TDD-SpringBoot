package com.exemplo.demo.servico.exception;

public class TelefoneNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public TelefoneNaoEncontradoException(String message) {
		super(message);
	}

}