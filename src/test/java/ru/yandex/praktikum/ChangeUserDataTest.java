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
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.praktikum.Constants.URL;
import static ru.yandex.praktikum.GenerateUser.*;

public class ChangeUserDataTest {
    String accessToken;
    int statusCode;
    String message;
    boolean isСhange;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Проверка - успешное изменение данных авторизованного пользователя")
    public void updateUserByAuthorization() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        accessToken = response.then().log().all().extract().path("accessToken");
        Response loginUser = StepUser.loginUser(RANDOM_EMAIL, RANDOM_PASSWORD, " ");
        Response changeUser = StepUser.changeUserAuthorization("test-data@yandex.ru", RANDOM_PASSWORD, RANDOM_NAME, accessToken);
        statusCode = response.then().log().all().extract().statusCode();
        boolean isСhange = response.then().log().all().extract().path("success");

        assertThat(statusCode, equalTo(200));
        assertThat(isСhange, equalTo(true));
    }
    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    @Description("Проверка - нельзя измененить данных неавторизованного пользователя")
    public void updateUserWithoutAuthorization() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        Response loginUser = StepUser.loginUser(RANDOM_EMAIL, RANDOM_PASSWORD, " ");
        Response changeUser = StepUser.changeUserNoAuthorization("test-data@yandex.ru", RANDOM_PASSWORD, RANDOM_NAME);
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isСhange = response.then().log().all().extract().path("success");

        assertThat(statusCode, equalTo(401));
        assertThat(message, equalTo("You should be authorised"));
        assertThat(isСhange, equalTo(false));
    }
    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken !=null) {
            StepUser.deleteUser(accessToken);
        }
    }
}
