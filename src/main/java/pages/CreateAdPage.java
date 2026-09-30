package pages;

import config.AppConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateAdPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By titleInput       = By.cssSelector("input[name='name']");
    private final By descriptionInput = By.cssSelector("input[name='description'], textarea[name='description']");
    private final By priceInput       = By.cssSelector("input[name='price']");
    private final By submitButton     = By.cssSelector("button[type='submit']");

    public CreateAdPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу создания объявления")
    public void open() {
        driver.get(AppConfig.BASE_URL + "/create-lisiting");
    }

    @Step("Ввести заголовок: {title}")
    public void fillTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput)).sendKeys(title);
    }

    @Step("Ввести описание")
    public void fillDescription(String description) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionInput)).sendKeys(description);
    }

    @Step("Ввести цену")
    public void fillPrice(String price) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceInput)).sendKeys(price);
    }

    @Step("Нажать опубликовать")
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Step("Создать объявление: {title}")
    public void createAd(String title, String description, String price) {
        fillTitle(title);
        fillDescription(description);
        fillPrice(price);
        clickSubmit();
    }

    @Step("Дождаться загрузки формы")
    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput));
    }
}
