package com.exemplo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.repository.helper.PessoaRepositoryQueries;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQueries {

	Optional<Pessoa> findByCpf(String cpf);

	@Query("SELECT p FROM Pessoa p JOIN p.telefones t WHERE t.ddd= :ddd AND t.numero = :numero")
	Optional<Pessoa> findByTelefoneAndTelefoneNumero(@Param ("ddd")String ddd, @Param("numero") String numero);

}
