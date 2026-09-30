package steps;

import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.LoginPage;
import utils.User;
import utils.UserApiClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizationSteps {

    private final ScenarioContext context;
    private final LoginPage loginPage;
    private final UserApiClient userApiClient = new UserApiClient();

    public AuthorizationSteps(ScenarioContext context) {
        this.context   = context;
        this.loginPage = new LoginPage(context.driver);
    }

    @Given("зарегистрированный пользователь создан через API")
    @Step("Создать зарегистрированного пользователя через API")
    public void createRegisteredUserViaApi() {
        context.currentUser = User.random();
        context.userSession = userApiClient.createUser(context.currentUser);
        context.accessToken = context.userSession.getToken();
    }

    @When("пользователь открывает страницу входа")
    @Step("Открыть страницу входа")
    public void openLoginPage() {
        loginPage.open();
    }

    @When("вводит корректные email и пароль")
    @Step("Ввод корректных учётных данных")
    public void enterValidCredentials() {
        loginPage.login(context.currentUser.getEmail(), context.currentUser.getPassword());
    }

    @Then("авторизация прошла успешно и отображается список объявлений")
    @Step("Проверка успешной авторизации по кнопке 'Выйти' в шапке")
    public void verifySuccessfulLogin() {
        assertTrue(loginPage.waitForLoggedIn(),
                "После успешного входа в шапке должна появиться кнопка 'Выйти'");
    }
}
