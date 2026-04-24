package br.com.nation.ui;

import br.com.nation.core.BaseUiTest;
import br.com.nation.factories.UsuarioFactory;
import br.com.nation.models.Usuario;
import br.com.nation.pages.LoginPage;
import br.com.nation.pages.ProdutosPage;
import br.com.nation.pages.ListaComprasPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("US005 - Jornada de Compra")
public class JornadaClienteUiTest extends BaseUiTest {

    @Test
    @DisplayName("CT001: Deve manipular a quantidade de um produto na lista com sucesso")
    public void deveAlterarQuantidadeDoProdutoNaLista() throws InterruptedException {
        Usuario user = UsuarioFactory.criarUsuarioComumValido();
        given().contentType("application/json").body(user).post("https://serverest.dev/usuarios");

        new LoginPage(driver).realizarLogin(user.getEmail(), user.getPassword());

        ProdutosPage produtosPage = new ProdutosPage(driver);
        produtosPage.buscarEAdicionarProduto("Logitech");

        ListaComprasPage listaPage = new ListaComprasPage(driver);

        Thread.sleep(1000);

        int qtdInicial = listaPage.obterQuantidadeAtual();
        System.out.println("Quantidade inicial: " + qtdInicial);

        listaPage.incrementar();
        assertEquals(qtdInicial + 1, listaPage.obterQuantidadeAtual(), "O incremento falhou!");

        listaPage.decrementar();
        assertEquals(qtdInicial, listaPage.obterQuantidadeAtual(), "O decremento falhou!");
    }

}