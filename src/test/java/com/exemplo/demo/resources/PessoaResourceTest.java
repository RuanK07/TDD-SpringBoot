package com.exemplo.demo.resources;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

import com.exemplo.demo.DemoApplicationTests;
import com.exemplo.demo.modelo.Pessoa;
import com.exemplo.demo.modelo.Telefone;

import io.restassured.http.ContentType;

public class PessoaResourceTest extends DemoApplicationTests{
	
	@Test
	void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
		given()
				.pathParam("ddd", "86")
				.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.OK.value())
				.body("id", equalTo(3),
					  "nome", equalTo("Cauê"),
					  "cpf", equalTo("38767897100"));
			
	}
	
	@Test
	void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() throws Exception {
		given()
				.pathParam("ddd", "99")
				.pathParam("numero", "987654321")
		.get("/pessoas/{ddd}/{numero}")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.body("message", equalTo("Não existe pessoa com o telefone (99)987654321"));
	}
	
	@Test
	void deve_salvar_nova_pessoa_no_sistema() throws Exception {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome("Lorenzo");
		pessoa.setCpf("27161503469");
		
		final Telefone telefone = new Telefone();
		telefone.setDdd("79");
		telefone.setNumero("36977168");
		
		pessoa.adicionaTelefone(telefone);
		
		given()
				.request()
				.header("Accept", ContentType.ANY)
				.header("Content-type", ContentType.JSON)
				.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
				.log().headers().and()
				.log().body().and()
				.statusCode(HttpStatus.CREATED.value())
				.header("Location", equalTo("http://localhost:" + porta + "/pessoas/79/36977168"))
				.body("id", equalTo(6),
					  "nome", equalTo("Lorenzo"),
					  "cpf", equalTo("27161503469"));
	}
	
	
	@Test
	void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome("Lorenzo");
		pessoa.setCpf("72788740417");
		
		final Telefone telefone = new Telefone();
		telefone.setDdd("79");
		telefone.setNumero("36977168");
		
		pessoa.adicionaTelefone(telefone);
		
		given()
				.request()
				.header("Accept", ContentType.ANY)
				.header("Content-type", ContentType.JSON)
				.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
				.log().body().and()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("message", equalTo("Já existe pessoa cadastrada com o CPF '72788740417'"));
	}
	
}
