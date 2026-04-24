package br.com.nation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProdutosPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By campoPesquisa = By.cssSelector("[data-testid='pesquisar']");
    private By botaoPesquisar = By.cssSelector("[data-testid='botaoPesquisar']");

    private By botaoAdicionarNaLista = By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/section/div[1]/div/div/a[2]/button");

    public ProdutosPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void buscarEAdicionarProduto(String nomeProduto) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoPesquisa)).sendKeys(nomeProduto);
        driver.findElement(botaoPesquisar).click();
        wait.until(ExpectedConditions.elementToBeClickable(botaoAdicionarNaLista)).click();
    }
}