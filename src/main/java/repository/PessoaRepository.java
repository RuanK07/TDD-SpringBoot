package repository;

import java.util.Optional;

import modelo.Pessoa;

public interface PessoaRepository {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);

}
