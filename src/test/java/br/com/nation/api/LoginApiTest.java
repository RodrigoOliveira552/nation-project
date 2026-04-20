package br.com.nation.api;

import br.com.nation.core.BaseApiTest;
import br.com.nation.factories.UsuarioFactory;
import br.com.nation.models.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("US002 - Autenticação de Usuários")
public class LoginApiTest extends BaseApiTest {

    @Test
    @DisplayName("CT001: Deve logar com sucesso usando credenciais válidas")
    public void deveLogarComSucesso() {
        Usuario usuarioValido = UsuarioFactory.criarUsuarioComumValido();
        given().body(usuarioValido).post("/usuarios").then().statusCode(201);

        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", usuarioValido.getEmail());
        credenciais.put("password", usuarioValido.getPassword());

        given()
                .body(credenciais)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue());
    }

    @Test
    @DisplayName("CT002: Deve bloquear login com senha incorreta")
    public void deveBloquearLoginComSenhaIncorreta() {
        Map<String, String> credenciaisInvalidas = new HashMap<>();
        credenciaisInvalidas.put("email", "fulano@qa.com");
        credenciaisInvalidas.put("password", "senha_errada_aqui");

        given()
                .body(credenciaisInvalidas)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(401)
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    @DisplayName("CT003: Deve barrar login com campos vazios")
    public void deveBarrarLoginComCamposVazios() {
        Map<String, String> credenciaisVazias = new HashMap<>();
        credenciaisVazias.put("email", "");
        credenciaisVazias.put("password", "");

        given()
                .body(credenciaisVazias)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"));
    }
}