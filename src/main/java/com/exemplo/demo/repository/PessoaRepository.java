package com.exemplo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemplo.demo.modelo.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Optional<Pessoa> findByCpf(String cpf);

	@Query("SELECT bean FROM Pessoa bean WHERE 1=1")
	Optional<Pessoa> findByTelefoneAndTelefoneNumero(String ddd, String numero);

}
