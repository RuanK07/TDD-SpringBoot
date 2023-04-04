package com.exemplo.demo.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exemplo.demo.servico.exception.TelefoneNaoEncontradoException;
import com.exemplo.demo.servico.exception.UnicidadeCpfException;

@RestControllerAdvice
public class ErroExceptionHandler {

	@ExceptionHandler({TelefoneNaoEncontradoException.class})
	public ResponseEntity<Erro> handleTelefoneNaoEncontradoException(TelefoneNaoEncontradoException e) {
		return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UnicidadeCpfException.class})
	public ResponseEntity<Erro> handleUnicidadeCpfException(UnicidadeCpfException e) {
		return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

}