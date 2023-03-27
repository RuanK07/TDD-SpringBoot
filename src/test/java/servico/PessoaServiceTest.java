package servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import modelo.Pessoa;
import modelo.Telefone;
import repository.PessoaRepository;
import servico.exception.TelefoneNaoEncontradoException;
import servico.exception.UnicidadeCpfException;
import servico.exception.UnicidadeTelefoneException;
import servico.impl.PessoaServiceImpl;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {
	
	private static final String NOME = "Ruan Kennedy";
	private static final String CPF = "12345678912";
	private static final String DDD = "55";
	private static final String NUMERO = "3212454452";
	
	@MockBean
	private PessoaRepository pessoaRepository;
	private PessoaService sut;
	private Pessoa pessoa;
	private Telefone telefone;
	
	@BeforeEach
	public void setUp() throws Exception {
		sut = new PessoaServiceImpl(pessoaRepository);
		
		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);
		
		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(NUMERO);
		
		pessoa.setTelefones(telefone);
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
	}
	
	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {
		sut.salvar(pessoa);
		
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
			
		assertThrows(UnicidadeCpfException.class, () -> sut.salvar(pessoa));
	}
	
	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		assertThrows(UnicidadeTelefoneException.class, () -> sut.salvar(pessoa));
	}
	
	@Test
	public void deve_retornar_excecao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
		assertThrows(TelefoneNaoEncontradoException.class, () -> sut.buscarPorTelefone(telefone));
	}
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);
		
		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);
		
		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}

}
