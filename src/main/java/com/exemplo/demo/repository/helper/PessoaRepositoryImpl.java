package com.exemplo.demo.repository.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.repository.filtro.PessoaFiltro;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Pessoa> filtrar(PessoaFiltro filtro) {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> params = new HashMap<>();
		
		sb.append("SELECT bean FROM Pessoa bean WHERE 1=1 ");
		
		preencherNomeSeNescessario(filtro, sb, params);
		preencherCpfSeNescessario(filtro, sb, params);
		
		TypedQuery<Pessoa> query = manager.createQuery(sb.toString(), Pessoa.class);
		preencherParametrosDaQuery(params, query);
		return query.getResultList();
	}

	private void preencherCpfSeNescessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if(StringUtils.hasText(filtro.getCpf())) {
			sb.append("AND bean.cpf LIKE :cpf ");
			params.put("cpf", "%" + filtro.getCpf() + "%");
		}
	}

	private void preencherNomeSeNescessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if(StringUtils.hasText(filtro.getNome())) {
			sb.append("AND bean.nome LIKE :nome ");
			params.put("nome", "%" + filtro.getNome() + "%");
		}
	}

	private void preencherParametrosDaQuery(final Map<String, Object> params, TypedQuery<Pessoa> query) {
		for(Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
	}

}
