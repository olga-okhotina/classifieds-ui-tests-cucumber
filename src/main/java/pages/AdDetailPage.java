package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By editButton   = By.xpath("//button[contains(.,'Редактировать объявление')]");
    private final By deleteButton = By.xpath("//button[contains(.,'Удалить')]");
    private final By titleInput   = By.cssSelector("input[name='name']");
    private final By saveButton   = By.cssSelector("button[type='submit']");
    private final By pageTitle    = By.cssSelector("h1, h2, [class*='title']");

    public AdDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Нажать редактировать")
    public void clickEdit() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }

    @Step("Нажать удалить")
    public void clickDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

    @Step("Изменить заголовок на: {newTitle}")
    public void updateTitle(String newTitle) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput)).clear();
        driver.findElement(titleInput).sendKeys(newTitle);
    }

    @Step("Сохранить изменения")
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    @Step("Дождаться загрузки страницы объявления")
    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }
}
