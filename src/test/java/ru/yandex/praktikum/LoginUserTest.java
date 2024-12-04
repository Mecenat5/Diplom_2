package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.praktikum.Constants.URL;
import static ru.yandex.praktikum.GenerateUser.*;

public class LoginUserTest {
    String accessToken;
    int statusCode;
    String message;
    boolean isLogin;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка - успешно залогиниться под существующим пользователем")
    public void loginingUser() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        Response loginUser = StepUser.loginUser(RANDOM_EMAIL, RANDOM_PASSWORD, " ");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        isLogin = response.then().log().all().extract().path("success");
        assertThat(accessToken, notNullValue());
        assertThat(statusCode, equalTo(200));
        assertThat(isLogin, equalTo(true));
    }
    @Test
    @DisplayName("Авторизация пользователя с невалидным логином")
    @Description("Проверка - не происходит авторизация пользователя с невалидным логином")
    public void loginingUserIncorrectLogin() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        Response loginUser = StepUser.loginUser("test54-data@yandex.ru", RANDOM_PASSWORD, " ");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isLogin = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(401));
        assertThat(message, equalTo("email or password are incorrect"));
        assertThat(isLogin, equalTo(false));
    }
    @Test
    @DisplayName("Авторизация пользователя с невалидным паролем")
    @Description("Проверка - не происходит авторизация пользователя с невалидным паролем")
    public void loginingUserIncorrectPassword() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        Response loginUser = StepUser.loginUser(RANDOM_EMAIL, "25854", " ");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isLogin = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(401));
        assertThat(message, equalTo("email or password are incorrect"));
        assertThat(isLogin, equalTo(false));
    }
    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken !=null) {
            StepUser.deleteUser(accessToken);
        }
    }
}
