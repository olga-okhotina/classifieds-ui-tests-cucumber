package pages;

import config.AppConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput      = By.cssSelector("input[name='email']");
    private final By passwordInput   = By.cssSelector("input[name='password']");
    private final By confirmPassword = By.cssSelector("input[name='submitPassword']");
    private final By submitButton    = By.cssSelector("button[type='submit']");
    private final By logoutButton    = By.xpath("//*[contains(text(),'Выйти')]");
    private final By loginButton     = By.xpath("//*[contains(text(),'Вход и регистрация')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(AppConfig.BASE_URL + "/registration");
    }

    @Step("Ввести email: {email}")
    public void fillEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void fillPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    @Step("Повторить пароль")
    public void fillConfirmPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword)).sendKeys(password);
    }

    @Step("Нажать кнопку регистрации")
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Step("Зарегистрироваться: {email}")
    public void register(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        fillConfirmPassword(password);
        clickSubmit();
    }

    @Step("Дождаться появления кнопки 'Выйти' в шапке")
    public boolean waitForLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)) != null;
    }

    @Step("Проверить, что пользователь не авторизован (видна кнопка 'Вход и регистрация')")
    public boolean isLoggedOut() {
        return !driver.findElements(loginButton).isEmpty();
    }

    @Step("Проверить, что форма регистрации видима")
    public boolean isFormVisible() {
        return !driver.findElements(emailInput).isEmpty()
                && driver.findElement(emailInput).isDisplayed();
    }
}
