package com.scheduly.api.web.controllers;

import com.scheduly.api.config.SecurityTestConfig;
import com.scheduly.api.infrastructure.auth.JwtFilter;
import com.scheduly.api.util.TestDataLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SecurityTestConfig.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClientControllerTest {

    @LocalServerPort
    private int port;

    @Mock
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @DisplayName("Deve retornar status 201 ao criar cliente com dados válidos")
    void deveRetornarStatus201_QuandoCriarClienteComDadosValidos() {
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("mariaSilva");

        given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body("name", equalTo("Maria Silva"))
                .body("cpf", equalTo("78621572060"))
                .body("email", equalTo("maria.silva@email.com"))
                .body("phone", equalTo("11987654321"))
                .body("id", notNullValue())
                .body("address.street", equalTo("Rua das Flores"));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao buscar cliente por ID existente")
    void deveRetornarStatus200_QuandoBuscarClientePorIdExistente() {
        // Primeiro, criar um cliente para buscar
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("joaoSantos");

        Integer clientId = given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Buscar o cliente criado
        given()
                .basePath("/clients/" + clientId)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", equalTo(clientId))
                .body("name", equalTo("João Santos"))
                .body("email", equalTo("joao.santos@email.com"));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao listar clientes")
    void deveRetornarStatus200_QuandoListarClientes() {
        given()
            .basePath("/clients")
            .accept(ContentType.JSON)
            .when()
            .get()
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Deve retornar status 404 ao buscar cliente por ID inexistente")
    void deveRetornarStatus404_QuandoBuscarClientePorIdInexistente() {
        given()
                .basePath("/clients/99999")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar status 200 ao buscar clientes por nome")
    void deveRetornarStatus200_QuandoBuscarClientesPorNome() {
        // Criar cliente com nome específico
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("anaCosta");

        given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());

        // Buscar por nome
        given()
                .basePath("/clients")
                .queryParam("name", "Ana")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar cliente existente")
    void deveRetornarStatus200_QuandoAtualizarClienteExistente() {
        // Criar cliente
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("pedroOliveira");

        Integer clientId = given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Aguardar um pouco para garantir que a transação foi commitada
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Atualizar cliente
        Map<String, Object> updateRequest = TestDataLoader.loadUpdateClient("pedroOliveiraAtualizado");

        given()
                .basePath("/clients/" + clientId)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(updateRequest)
            .when()
                .patch()
            .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", equalTo(clientId))
                .body("name", equalTo("Pedro Oliveira Santos"))
                .body("phone", equalTo("11999999999"))
                .body("address.street", equalTo("Av. Faria Lima"));
    }

    @Test
    @DisplayName("Deve retornar status 204 ao deletar cliente existente")
    void deveRetornarStatus204_QuandoDeletarClienteExistente() {
        // Criar cliente
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("clienteParaDeletar");

        var clientId = given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Deletar cliente
        given()
                .basePath("/clients/" + clientId)
            .when()
                .delete()
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Verificar que o cliente foi deletado
        given()
                .basePath("/clients/" + clientId)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar status 400 ao criar cliente com dados inválidos")
    void deveRetornarStatus400_QuandoCriarClienteComDadosInvalidos() {
        Map<String, Object> clientRequest = TestDataLoader.loadInvalidClient("clienteInvalido");

        given()
                .basePath("/clients")
                .contentType(ContentType.JSON)
                .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar status 404 ao atualizar cliente inexistente")
    void deveRetornarStatus404_QuandoAtualizarClienteInexistente() {
        Map<String, Object> updateRequest = TestDataLoader.loadUpdateClient("clienteInexistente");

        given()
                .basePath("/clients/99999")
                .contentType(ContentType.JSON)
                .body(updateRequest)
            .when()
                .patch()
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
