package steps;

import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AdDetailPage;
import pages.AdsPage;
import pages.CreateAdPage;
import config.AppConfig;
import pages.LoginPage;
import utils.User;
import utils.UserApiClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdSteps {

    private final ScenarioContext context;
    private final LoginPage loginPage;
    private final AdsPage adsPage;
    private final CreateAdPage createAdPage;
    private final AdDetailPage adDetailPage;
    private final UserApiClient userApiClient = new UserApiClient();

    public AdSteps(ScenarioContext context) {
        this.context       = context;
        this.loginPage     = new LoginPage(context.driver);
        this.adsPage       = new AdsPage(context.driver);
        this.createAdPage  = new CreateAdPage(context.driver);
        this.adDetailPage  = new AdDetailPage(context.driver);
    }

    @Given("авторизованный пользователь создан через API и вошёл в систему")
    @Step("Создать пользователя через API и войти через UI")
    public void createUserViaApiAndLogin() {
        context.currentUser = User.random();
        context.userSession = userApiClient.createUser(context.currentUser);
        context.accessToken = context.userSession.getToken();
        loginPage.open();
        loginPage.login(context.currentUser.getEmail(), context.currentUser.getPassword());
        loginPage.waitForLoggedIn();
        context.driver.get(AppConfig.BASE_URL + "/");
        adsPage.waitForPage();
    }

    @Given("у пользователя есть созданное объявление")
    @Step("Создать тестовое объявление")
    public void userHasCreatedAd() {
        context.createdAdTitle = "Ad_" + UUID.randomUUID().toString().substring(0, 8);
        createAdPage.open();
        createAdPage.waitForPage();
        createAdPage.createAd(context.createdAdTitle, "Тестовое описание", "1000");
        adsPage.waitForPage();
    }

    @When("пользователь нажимает создать объявление")
    @Step("Перейти на страницу создания объявления")
    public void clickCreateAd() {
        createAdPage.open();
        createAdPage.waitForPage();
    }

    @When("заполняет форму объявления корректными данными")
    @Step("Заполнение формы нового объявления")
    public void fillAdForm() {
        context.createdAdTitle = "Ad_" + UUID.randomUUID().toString().substring(0, 8);
        createAdPage.createAd(context.createdAdTitle, "Описание тестового объявления", "500");
    }

    @When("открывает своё объявление")
    @Step("Открыть своё объявление из главной страницы")
    public void openOwnAd() {
        adsPage.openAdByTitle(context.createdAdTitle);
        adDetailPage.waitForPage();
    }

    @When("редактирует заголовок объявления")
    @Step("Нажать редактировать и изменить заголовок")
    public void editAdTitle() {
        adDetailPage.clickEdit();
        createAdPage.waitForPage();
        String updatedTitle = context.createdAdTitle + "_edited";
        adDetailPage.updateTitle(updatedTitle);
        adDetailPage.clickSave();
        context.createdAdTitle = updatedTitle;
    }

    @When("удаляет объявление")
    @Step("Нажать удалить объявление")
    public void deleteAd() {
        adDetailPage.clickDelete();
    }

    @Then("объявление отображается в списке")
    @Step("Проверить, что объявление есть в списке")
    public void adIsVisibleInList() {
        adsPage.waitForPage();
        assertTrue(adsPage.hasAdWithTitle(context.createdAdTitle),
                "Объявление '" + context.createdAdTitle + "' должно отображаться в списке");
    }

    @Then("объявление содержит обновлённый заголовок")
    @Step("Проверить обновлённый заголовок")
    public void adHasUpdatedTitle() {
        adsPage.open();
        adsPage.waitForPage();
        assertTrue(adsPage.hasAdWithTitle(context.createdAdTitle),
                "Обновлённый заголовок '" + context.createdAdTitle + "' должен отображаться");
    }

    @Then("объявление отсутствует в списке")
    @Step("Проверить, что объявление удалено")
    public void adIsNotVisibleInList() {
        adsPage.open();
        adsPage.waitForPage();
        assertTrue(adsPage.hasNoAdWithTitle(context.createdAdTitle),
                "Удалённое объявление '" + context.createdAdTitle + "' не должно отображаться");
    }
}
