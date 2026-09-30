package utils;

import config.AppConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String SIGNUP = "/signup";

    @Step("Создать пользователя через API: {user.email}")
    public UserSession createUser(User user) {
        String token = given()
                .contentType(ContentType.JSON)
                .baseUri(AppConfig.API_BASE_URL)
                .body(Map.of("email", user.getEmail(), "password", user.getPassword()))
                .when().post(SIGNUP)
                .then().statusCode(201)
                .extract().path("access_token.access_token");
        return new UserSession(user, token);
    }
}
