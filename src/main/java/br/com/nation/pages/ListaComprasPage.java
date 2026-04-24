package br.com.nation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ListaComprasPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By botaoDecrementar = By.xpath("//*[@id=\"root\"]/div/div/div/div/div/section/div[1]/div/div[3]/button[1]");
    private By botaoIncrementar = By.xpath("//*[@id=\"root\"]/div/div/div/div/div/section/div[1]/div/div[3]/button[2]");
    private By campoQuantidade = By.xpath("//*[@id=\"root\"]/div/div/div/div/div/section/div[1]/div/div[3]/div[2]/p");

    public ListaComprasPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void incrementar() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoIncrementar)).click();
    }

    public void decrementar() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoDecrementar)).click();
    }

    public int obterQuantidadeAtual() {
        String textoBruto = wait.until(ExpectedConditions.visibilityOfElementLocated(campoQuantidade)).getText();

        String apenasNumeros = textoBruto.trim();

        return Integer.parseInt(apenasNumeros);
    }
}