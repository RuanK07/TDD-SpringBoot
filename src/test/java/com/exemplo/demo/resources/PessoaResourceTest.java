package com.exemplo.demo.resources;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

import com.example.demo.DemoApplicationTests;

public class PessoaResourceTest extends DemoApplicationTests{
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
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
}