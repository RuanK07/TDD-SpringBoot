package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	
	private String nome;
	private String cpf;
	private List<Endereco> enderecos = new ArrayList<>();
	private List<Telefone> telefones = new ArrayList<>();
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(Telefone telefone) {
		telefones.add(telefone);
	}
	
	

}
