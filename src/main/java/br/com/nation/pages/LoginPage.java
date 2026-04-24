package br.com.nation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoEmail = By.cssSelector("[data-testid='email']");
    private By campoSenha = By.cssSelector("[data-testid='senha']");
    private By botaoEntrar = By.cssSelector("[data-testid='entrar']");
    private By alertaErro = By.cssSelector(".alert");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void realizarLogin(String email, String senha) {
        wait.until(ExpectedConditions.elementToBeClickable(campoEmail)).clear();
        driver.findElement(campoEmail).sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(campoSenha)).clear();
        driver.findElement(campoSenha).sendKeys(senha);

        wait.until(ExpectedConditions.elementToBeClickable(botaoEntrar)).click();
    }

    public String getUrlAtual() {
        wait.until(ExpectedConditions.urlContains("/home"));
        return driver.getCurrentUrl();
    }

    public String getMensagemDeErro() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertaErro)).getText();
    }
}