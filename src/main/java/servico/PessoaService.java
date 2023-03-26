package servico;

import modelo.Pessoa;
import servico.exception.UnicidadeCpfException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException;

}
