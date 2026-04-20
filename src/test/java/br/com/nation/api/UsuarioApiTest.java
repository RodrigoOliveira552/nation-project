package br.com.nation.api;

import br.com.nation.core.BaseApiTest;
import br.com.nation.factories.UsuarioFactory;
import br.com.nation.models.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("US001 - Gestão de Usuários")
public class UsuarioApiTest extends BaseApiTest {

    @Test
    @DisplayName("CT001: Deve cadastrar um novo Administrador com sucesso")
    public void deveCadastrarAdministrador() {
        Usuario novoAdmin = UsuarioFactory.criarUsuarioAdminValido();

        given()
                .body(novoAdmin)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("CT002: Deve cadastrar um Usuário Comum com sucesso")
    public void deveCadastrarUsuarioComum() {
        Usuario novoComum = UsuarioFactory.criarUsuarioComumValido();

        given()
                .body(novoComum)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("CT003: Deve bloquear cadastro com email já existente")
    public void deveBloquearCadastroComEmailDuplicado() {
        Usuario usuarioDuplicado = UsuarioFactory.criarUsuarioAdminValido();

        given().body(usuarioDuplicado).post("/usuarios").then().statusCode(201);

        given()
                .body(usuarioDuplicado)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }

    @Test
    @DisplayName("CT004: Deve barrar cadastro com campos obrigatórios vazios")
    public void deveBarrarCadastroComCamposVazios() {
        Usuario usuarioVazio = Usuario.builder()
                .nome("")
                .email("")
                .password("")
                .administrador("")
                .build();

        given()
                .body(usuarioVazio)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"))
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"))
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    @DisplayName("CT005: Deve barrar cadastro com tipo de dado incompatível no Enum Administrador")
    public void deveBarrarCadastroComDadoIncompativel() {
        Usuario usuarioIncompativel = UsuarioFactory.criarUsuarioComumValido();
        usuarioIncompativel.setAdministrador("talvez");

        given()
                .body(usuarioIncompativel)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(400)
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }
}