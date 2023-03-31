package com.exemplo.demo.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.modelo.Telefone;
import com.exemplo.demo.repository.PessoaRepository;
import com.exemplo.demo.servico.PessoaService;
import com.exemplo.demo.servico.exception.TelefoneNaoEncontradoException;
import com.exemplo.demo.servico.exception.UnicidadeCpfException;
import com.exemplo.demo.servico.exception.UnicidadeTelefoneException;

import servico.impl.PessoaServiceImpl;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

	private static final String NOME = "Eduardo Freitas";
	private static final String CPF = "1234567890";
	private static final String DDD = "55";
	private static final String NUMERO = "0987654321";

	@MockBean
	private PessoaRepository pessoaRepository;

	private PessoaService sut;

	private Pessoa pessoa;
	private Telefone telefone;

	@BeforeEach
	void setUp() throws Exception {
		sut = new PessoaServiceImpl(pessoaRepository);

		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);

		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(NUMERO);

		pessoa.adicionaTelefone(telefone);
	}

	@Test
	void deveSalvarPessoaNoRepositorio() throws Exception {
		sut.salvar(pessoa);

		verify(pessoaRepository).save(pessoa);
	}

	@Test
	void naoDeveSalvarDuasPessoasComOMesmoCPF() throws Exception {
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

		UnicidadeCpfException e = assertThrows(UnicidadeCpfException.class, () -> sut.salvar(pessoa));
		assertEquals("Já existe pessoa cadastrada com o CPF '" + CPF + "'", e.getMessage());
	}

	@Test
	void naoDeveSalvarDuasPessoaComOMesmoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		assertThrows(UnicidadeTelefoneException.class, () -> sut.salvar(pessoa));
	}

	@Test
	void deveRetornarExcecaoDeNaoEncontradoQuandoNaoExistirPessoaComODddENumeroDeTelefone() throws Exception {
		assertThrows(TelefoneNaoEncontradoException.class, () -> sut.buscarPorTelefone(telefone));
	}
	
	@Test
	void deveRetornarDadosDoTelefoneDentroDaExcecaoDeTelefoneNaoEncontradoException() throws Exception {
		TelefoneNaoEncontradoException e = assertThrows(TelefoneNaoEncontradoException.class, () -> sut.buscarPorTelefone(telefone));
		assertEquals("Não existe pessoa com o telefone (" + DDD + ")" + NUMERO, e.getMessage());
	}

	@Test
	void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);

		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}

}
