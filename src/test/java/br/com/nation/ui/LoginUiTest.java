package br.com.nation.ui;

import br.com.nation.core.BaseUiTest;
import br.com.nation.factories.UsuarioFactory;
import br.com.nation.models.Usuario;
import br.com.nation.pages.HomePage;
import br.com.nation.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("US003 - Autenticação Web")
public class LoginUiTest extends BaseUiTest {

    @Test
    @DisplayName("CT001: Deve logar com sucesso usando um usuário válido criado via API")
    public void deveLogarComSucesso() {
        Usuario usuarioValido = UsuarioFactory.criarUsuarioComumValido();
        given().contentType("application/json").body(usuarioValido).post("https://serverest.dev/usuarios");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLogin(usuarioValido.getEmail(), usuarioValido.getPassword());

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isBotaoLogoutVisivel(), "O botão de Logout não apareceu na tela!");

        // Dica de Sênior: Guarda o texto real numa variável e imprime no console para você ver
        String textoRealDaTela = homePage.getTextoBoasVindas();
        System.out.println(" Título na tela está visível: " + textoRealDaTela);

        assertTrue(textoRealDaTela.contains("Serverest Store"), "O texto de boas vindas da loja não foi exibido!"); }

    @Test
    @DisplayName("CT002: Deve bloquear acesso com senha inválida")
    public void deveBloquearLoginComSenhaIncorreta(){
        LoginPage loginPage = new LoginPage(driver);

        loginPage.realizarLogin("teste@qa.com.br", "senha_completamente_errada");

        String mensagemErro = loginPage.getMensagemDeErro();

        assertTrue(mensagemErro.contains("Email e/ou senha inválidos"),
                "A mensagem de erro não conteve o texto esperado: " + mensagemErro);
    }

    @Test
    @DisplayName("CT003: Deve barrar login com campos vazios")
    public void deveBarrarLoginComCamposVazios(){
        LoginPage loginPage = new LoginPage(driver);

        loginPage.realizarLogin("", "");

        String mensagemErro = loginPage.getMensagemDeErro();

        assertTrue(mensagemErro.contains("Email é obrigatório"),
                "A mensagem de erro não conteve o texto esperado: " + mensagemErro);
    }
}