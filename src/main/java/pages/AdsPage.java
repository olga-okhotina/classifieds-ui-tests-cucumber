package pages;

import config.AppConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By header = By.cssSelector("header, nav, [class*='header']");

    public AdsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(AppConfig.BASE_URL + "/");
    }

    @Step("Дождаться загрузки страницы")
    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    @Step("Найти и открыть объявление через поиск на главной: {title}")
    public void openAdByTitle(String title) {
        driver.get(AppConfig.BASE_URL + "/");
        By searchInput = By.cssSelector("input[placeholder*='купить']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(title, Keys.ENTER);
        By byTitle = By.xpath("//h2[contains(.,'" + title + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTitle)).click();
    }

    @Step("Проверить, что объявление есть в профиле: {title}")
    public boolean hasAdWithTitle(String title) {
        driver.get(AppConfig.BASE_URL + "/profile");
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        By byTitle = By.xpath("//h2[contains(.,'" + title + "')]");
        return wait.until(d -> !d.findElements(byTitle).isEmpty());
    }

    @Step("Проверить, что объявление отсутствует в профиле: {title}")
    public boolean hasNoAdWithTitle(String title) {
        driver.get(AppConfig.BASE_URL + "/profile");
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        By byTitle = By.xpath("//h2[contains(.,'" + title + "')]");
        return wait.until(d -> d.findElements(byTitle).isEmpty());
    }
}
