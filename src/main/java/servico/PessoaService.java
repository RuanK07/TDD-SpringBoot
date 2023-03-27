package servico;

import modelo.Pessoa;
import modelo.Telefone;
import servico.exception.TelefoneNaoEncontradoException;
import servico.exception.UnicidadeCpfException;
import servico.exception.UnicidadeTelefoneException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

	Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;

}
