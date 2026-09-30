package steps;

import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.RegisterPage;
import utils.User;
import utils.UserApiClient;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationSteps {

    private final ScenarioContext context;
    private final RegisterPage registerPage;
    private final UserApiClient userApiClient = new UserApiClient();

    public RegistrationSteps(ScenarioContext context) {
        this.context      = context;
        this.registerPage = new RegisterPage(context.driver);
    }

    @Given("уникальный пользователь подготовлен для регистрации")
    @Step("Генерация уникального пользователя")
    public void prepareUniqueUser() {
        context.currentUser = User.random();
    }

    @Given("пользователь уже зарегистрирован через API")
    @Step("Регистрация пользователя через API")
    public void userAlreadyRegisteredViaApi() {
        context.currentUser = User.random();
        context.userSession = userApiClient.createUser(context.currentUser);
        context.accessToken = context.userSession.getToken();
    }

    @When("пользователь открывает страницу регистрации")
    @Step("Открыть страницу регистрации")
    public void openRegisterPage() {
        registerPage.open();
    }

    @When("заполняет форму регистрации корректными данными")
    @Step("Заполнение формы регистрации корректными данными")
    public void fillRegisterFormWithValidData() {
        registerPage.register(context.currentUser.getEmail(), context.currentUser.getPassword());
    }

    @When("заполняет форму регистрации данными уже существующего пользователя")
    @Step("Заполнение формы данными существующего пользователя")
    public void fillRegisterFormWithExistingUser() {
        registerPage.register(context.currentUser.getEmail(), context.currentUser.getPassword());
    }

    @Then("регистрация прошла успешно и пользователь авторизован")
    @Step("Проверка успешной регистрации по кнопке 'Выйти' в шапке")
    public void verifySuccessfulRegistration() {
        assertTrue(registerPage.waitForLoggedIn(),
                "После успешной регистрации в шапке должна появиться кнопка 'Выйти'");
    }

    @Then("регистрация не выполнена и форма остаётся видимой")
    @Step("Проверка, что повторная регистрация не выполнена")
    public void verifyDuplicateRegistrationError() {
        assertTrue(registerPage.isFormVisible(),
                "Форма регистрации должна оставаться видимой при ошибке");
        assertTrue(registerPage.isLoggedOut(),
                "Пользователь не должен быть авторизован — в шапке должна оставаться кнопка 'Вход и регистрация'");
    }
}
