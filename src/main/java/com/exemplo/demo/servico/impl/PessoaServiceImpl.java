package com.exemplo.demo.servico.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.modelo.Telefone;
import com.exemplo.demo.repository.PessoaRepository;
import com.exemplo.demo.servico.PessoaService;
import com.exemplo.demo.servico.exception.TelefoneNaoEncontradoException;
import com.exemplo.demo.servico.exception.UnicidadeCpfException;
import com.exemplo.demo.servico.exception.UnicidadeTelefoneException;

@Service
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

		if (optional.isPresent()) {
			throw new UnicidadeCpfException("Já existe pessoa cadastrada com o CPF '" + pessoa.getCpf() + "'");
		}

		String ddd = pessoa.getTelefones().get(0).getDdd();
		String numero = pessoa.getTelefones().get(0).getNumero();
		optional = pessoaRepository.findByTelefoneAndTelefoneNumero(ddd, numero);

		if (optional.isPresent()) {
			throw new UnicidadeTelefoneException();
		}

		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(() -> new TelefoneNaoEncontradoException("Não existe pessoa com o telefone (" + telefone.getDdd() + ")" + telefone.getNumero()));
	}

}
