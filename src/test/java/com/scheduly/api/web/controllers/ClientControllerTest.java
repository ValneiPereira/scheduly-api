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

@ActiveProfiles("test")
@Import(SecurityTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    @DisplayName("Deve retornar status 200 ao buscar cliente por ID existente")
    void deveRetornarStatus200_QuandoBuscarClientePorIdExistente() {
        // Criar cliente via registro (endpoint disponível)
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("joaoSantos");
        String email = (String) clientRequest.get("email");
        Map<String, Object> registerRequest = Map.of(
            "name", clientRequest.get("name"),
            "email", email,
            "phone", clientRequest.get("phone"),
            "password", "senha123"
        );

        // @formatter:off
        // Registrar cliente
        given()
                .basePath("/auth/register")
                .contentType(ContentType.JSON)
                .body(registerRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());

        // Buscar cliente na lista completa pelo email
        var allClients = given()
                .basePath("/clients")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("", Map.class);

        // Encontrar cliente pelo email
        var client = allClients.stream()
                .filter(c -> email.equals(((Map<?, ?>) c).get("email")))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cliente não encontrado após registro"));

        var clientId = ((Map<?, ?>) client).get("id");
        
        // Buscar por ID
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
        // @formatter:on
    }

    @Test
    @DisplayName("Deve retornar status 200 ao listar clientes")
    void deveRetornarStatus200_QuandoListarClientes() {
        // @formatter:off
            given()
                .basePath("/clients")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
        ;
        // @formatter:on

    }

    @Test
    @DisplayName("Deve retornar status 404 ao buscar cliente por ID inexistente")
    void deveRetornarStatus404_QuandoBuscarClientePorIdInexistente() {
        // @formatter:off
        given()
            .basePath("/clients/99999")
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
        // @formatter:on
    }


    @Test
    @DisplayName("Deve retornar status 200 ao buscar clientes por nome")
    void deveRetornarStatus200_QuandoBuscarClientesPorNome() {
        // Criar cliente via registro
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("anaCosta");
        String name = (String) clientRequest.get("name");

        Map<String, Object> registerRequest = Map.of(
            "name", name,
            "email", clientRequest.get("email"),
            "phone", clientRequest.get("phone"),
            "password", "senha123"
        );

        given()
            .basePath("/auth/register")
            .contentType(ContentType.JSON)
            .body(registerRequest)
            .when()
            .post()
            .then()
            .statusCode(HttpStatus.CREATED.value());

        // Buscar por nome (usando parte do nome)
        given()
            .basePath("/clients/search")
            .queryParam("name", "Ana")
            .accept(ContentType.JSON)
            .when()
            .get()
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("$", not(empty()))
            .body("[0].name", containsString("Ana"))
        ;
    }

    // Teste removido: PATCH /clients/{id} foi removido da API (clientes atualizam via /clients/me)

    @Test
    @DisplayName("Deve retornar status 204 ao deletar cliente existente")
    void deveRetornarStatus204_QuandoDeletarClienteExistente() {
        // Criar cliente via registro
        Map<String, Object> clientRequest = TestDataLoader.loadValidClient("clienteParaDeletar");
        String email = (String) clientRequest.get("email");
        Map<String, Object> registerRequest = Map.of(
            "name", clientRequest.get("name"),
            "email", email,
            "phone", clientRequest.get("phone"),
            "password", "senha123"
        );

        // @formatter:off
        // Registrar cliente
        given()
                .basePath("/auth/register")
                .contentType(ContentType.JSON)
                .body(registerRequest)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());

        // Buscar cliente na lista completa pelo email
        var allClients = given()
                .basePath("/clients")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("", Map.class);

        // Encontrar cliente pelo email
        var client = allClients.stream()
                .filter(c -> email.equals(((Map<?, ?>) c).get("email")))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cliente não encontrado após registro"));

        var clientId = ((Map<?, ?>) client).get("id");
        
        // Deletar cliente
        given()
                .basePath("/clients/" + clientId)
            .when()
                .delete()
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
        ;

        // Verificar que o cliente foi deletado
        given()
                .basePath("/clients/" + clientId)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
        // @formatter:on
    }

    // Teste removido: POST /clients foi removido da API (validação de criação via /auth/register)

    // Teste removido: PATCH /clients/{id} foi removido da API


}
