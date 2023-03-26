package servico.impl;

import java.util.Optional;

import modelo.Pessoa;
import repository.PessoaRepository;
import servico.PessoaService;
import servico.exception.UnicidadeCpfException;

public class PessoaServiceImpl implements PessoaService {
	
	private final PessoaRepository pessoaRepository;
	
	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException{
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		return pessoaRepository.save(pessoa);
	}
}