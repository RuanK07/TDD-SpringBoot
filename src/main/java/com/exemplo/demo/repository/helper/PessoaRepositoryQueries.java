package com.exemplo.demo.repository.helper;

import java.util.List;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.repository.filtro.PessoaFiltro;

public interface PessoaRepositoryQueries {
	
	List<Pessoa> filtrar(PessoaFiltro filtro);
}
