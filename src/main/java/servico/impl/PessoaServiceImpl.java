package servico.impl;

import java.util.Optional;

import modelo.Pessoa;
import modelo.Telefone;
import repository.PessoaRepository;
import servico.PessoaService;
import servico.exception.TelefoneNaoEncontradoException;
import servico.exception.UnicidadeCpfException;
import servico.exception.UnicidadeTelefoneException;

public class PessoaServiceImpl implements PessoaService {
	
	private final PessoaRepository pessoaRepository;
	
	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException{
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		final String ddd = pessoa.getTelefones().get(0).getDdd();
		final String numero = pessoa.getTelefones().get(0).getNumero();
		optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero);
		
		if( optional.isPresent() ) {;
			throw new UnicidadeTelefoneException();
		}
		
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
		final Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(TelefoneNaoEncontradoException::new);
	}
}