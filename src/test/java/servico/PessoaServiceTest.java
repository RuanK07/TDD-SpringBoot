package servico;

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
import repository.PessoaRepository;
import servico.exception.UnicidadeCpfException;
import servico.impl.PessoaServiceImpl;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {
	
	private static final String NOME = "Ruan Kennedy";
	private static final String CPF = "12345678912";
	
	@MockBean
	private PessoaRepository pessoaRepository;
	
	private PessoaService sut;
	
	private Pessoa pessoa;
	
	@BeforeEach
	public void setUp() throws Exception {
		sut = new PessoaServiceImpl(pessoaRepository);
		
		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
	}
	
	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {
		sut.salvar(pessoa);
		
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		assertThrows(UnicidadeCpfException.class, () -> {
			when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
			
			sut.salvar(pessoa);
		});
	}

}
