package pages;

import config.AppConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput    = By.cssSelector("input[name='email']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By submitButton  = By.cssSelector("button[type='submit']");
    private final By header        = By.cssSelector("header, nav, [class*='header']");
    private final By logoutButton  = By.xpath("//*[contains(text(),'Выйти')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(AppConfig.BASE_URL + "/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        driver.get(AppConfig.BASE_URL + "/login");
    }

    @Step("Ввести email: {email}")
    public void fillEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void fillPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    @Step("Нажать кнопку входа")
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Step("Войти: {email}")
    public void login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickSubmit();
    }

    @Step("Дождаться появления кнопки 'Выйти' в шапке")
    public boolean waitForLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)) != null;
    }
}
