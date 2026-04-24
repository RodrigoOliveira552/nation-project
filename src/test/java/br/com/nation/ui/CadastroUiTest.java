package br.com.nation.ui;

import br.com.nation.core.BaseUiTest;
import br.com.nation.factories.UsuarioFactory;
import br.com.nation.models.Usuario;
import br.com.nation.pages.CadastroPage;
import br.com.nation.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("US004 - Cadastro de Usuários Web no Front-end")
public class CadastroUiTest extends BaseUiTest {

    @Test
    @DisplayName("CT001: Deve cadastrar um usuário comum com sucesso na tela")
    public void deveCadastrarUsuarioComSucesso() {
        Usuario novoUsuario = UsuarioFactory.criarUsuarioComumValido();

        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.irParaTelaDeCadastro();
        cadastroPage.realizarCadastro(
                novoUsuario.getNome(),
                novoUsuario.getEmail(),
                novoUsuario.getPassword(),
                false
        );

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isBotaoLogoutVisivel(), "O botão de Logout não apareceu após o cadastro!");
        assertTrue(homePage.getTextoBoasVindas().contains("Serverest Store"), "Não foi redirecionado para a loja corretamente!");
    }

    @Test
    @DisplayName("CT002: Deve barrar cadastro de email já existente")
    public void deveBarrarCadastroEmailDuplicado() {
        Usuario usuarioAntigo = UsuarioFactory.criarUsuarioComumValido();
        given().contentType("application/json").body(usuarioAntigo).post("https://serverest.dev/usuarios");

        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.irParaTelaDeCadastro();
        cadastroPage.realizarCadastro(
                usuarioAntigo.getNome(),
                usuarioAntigo.getEmail(),
                usuarioAntigo.getPassword(),
                false
        );

        String erroNaTela = cadastroPage.getMensagemDeErro();
        assertTrue(erroNaTela.contains("Este email já está sendo usado"),
                "O erro esperado não apareceu. Apareceu isto: " + erroNaTela);
    }

    @Test
    @DisplayName("CT003: Deve cadastrar um Administrador com sucesso na tela")
    public void deveCadastrarAdministradorComSucesso() {
        Usuario novoAdmin = UsuarioFactory.criarUsuarioAdminValido();

        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.irParaTelaDeCadastro();
        cadastroPage.realizarCadastro(
                novoAdmin.getNome(),
                novoAdmin.getEmail(),
                novoAdmin.getPassword(),
                true
        );

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isBotaoLogoutVisivel(), "O botão de Logout não apareceu após o cadastro do Admin!");

        String textoRealDaTela = homePage.getTextoBoasVindas();
        assertTrue(textoRealDaTela.contains("Bem Vindo"),
                "O Administrador não foi redirecionado para o painel correto! Texto encontrado: " + textoRealDaTela);
    }

    @Test
    @DisplayName("CT004: Deve barrar cadastro com campos obrigatórios vazios")
    public void deveBarrarCadastroComCamposVazios() {
        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.irParaTelaDeCadastro();

        cadastroPage.realizarCadastro("", "", "", false);

        String erroNaTela = cadastroPage.getMensagemDeErro();
        assertTrue(erroNaTela.contains("Nome é obrigatório") || erroNaTela.contains("nome não pode ficar em branco"),
                "A validação de campo vazio falhou ou a mensagem mudou. Texto encontrado: " + erroNaTela);
    }
}