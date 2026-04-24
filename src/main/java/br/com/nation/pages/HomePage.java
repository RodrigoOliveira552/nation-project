package br.com.nation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By botaoLogout = By.cssSelector("[data-testid='logout']");
    private By tituloBoasVindas = By.cssSelector("h1");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isBotaoLogoutVisivel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(botaoLogout)).isDisplayed();
    }

    public String getTextoBoasVindas() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tituloBoasVindas)).getText();
    }
}