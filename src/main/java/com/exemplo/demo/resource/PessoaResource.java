package com.exemplo.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.modelo.Telefone;
import com.exemplo.demo.servico.PessoaService;
import com.exemplo.demo.servico.exception.TelefoneNaoEncontradoException;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;

	@GetMapping("/{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd,
																@PathVariable("numero") String numero) throws TelefoneNaoEncontradoException {
		
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);
		
		final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
		
		return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
	}
}
