package br.com.nation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CadastroPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By linkCadastreSe = By.cssSelector("[data-testid='cadastrar']");
    private By campoNome = By.cssSelector("[data-testid='nome']");
    private By campoEmail = By.cssSelector("[data-testid='email']");
    private By campoSenha = By.cssSelector("[data-testid='password']");
    private By checkboxAdmin = By.cssSelector("[data-testid='checkbox']");
    private By botaoCadastrar = By.cssSelector("[data-testid='cadastrar']");

    private By alertaErro = By.cssSelector(".alert");

    public CadastroPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void irParaTelaDeCadastro() {
        wait.until(ExpectedConditions.elementToBeClickable(linkCadastreSe)).click();
    }

    public void realizarCadastro(String nome, String email, String senha, boolean isAdmin) {
        wait.until(ExpectedConditions.elementToBeClickable(campoNome)).sendKeys(nome);
        driver.findElement(campoEmail).sendKeys(email);
        driver.findElement(campoSenha).sendKeys(senha);

        if (isAdmin) {
            driver.findElement(checkboxAdmin).click();
        }

        driver.findElement(botaoCadastrar).click();
    }

    public String getMensagemDeErro() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertaErro)).getText();
    }
}