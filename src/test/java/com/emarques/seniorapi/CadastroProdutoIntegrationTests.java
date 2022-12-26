package com.emarques.seniorapi;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ProdutoRepository;
import com.emarques.seniorapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroProdutoIT {

	private static final String PRODUTO_ID_INEXISTENTE = "c850df68-5cf0-4912-ab5b-9025749ef169";

	@LocalServerPort
	private int port;

	@Autowired
	private ProdutoRepository produtoRepository;

	private Produto produto;

	private Produto servico;

	private String jsonCorretoProduto;



	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/v1/produtos";

		jsonCorretoProduto = ResourceUtils.getContentFromResource(
				"/json/correto/produto.json");

		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutos() {
		given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarProduto() {
		given()
				.body(jsonCorretoProduto)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.post()
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarProdutoExistente() {
		given()
				.pathParam("produtoId", produto.getId())
				.accept(ContentType.JSON)
				.when()
				.get("/{produtoId}")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(produto.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarProdutoInexistente() {
		given()
				.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
				.accept(ContentType.JSON)
				.when()
				.get("/{produtoId}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		produto = new Produto();
		produto.setNome("Notebook");
		produto.setDescricao("Notebook turbinado da positivo");
		produto.setPreco(BigDecimal.valueOf(1200));
		produto.setTipoItem(TipoItem.PRODUTO);
		produto = produtoRepository.save(produto);

		servico = new Produto();
		servico.setNome("Formatar Computador");
		servico.setDescricao("Formatar o computador e instalar programas");
		servico.setPreco(BigDecimal.valueOf(125));
		servico.setTipoItem(TipoItem.SERVICO);
		servico = produtoRepository.save(servico);
	}

}
