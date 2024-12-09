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

public class CreateUserTest {

    String accessToken;
    int statusCode;
    boolean isCreate;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Создать пользователя")
    @Description("Проверка - успешное создание нового пользователя")
    public void creatingUser() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo (200));
        assertThat(isCreate, equalTo(true));

    }
    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    @Description("Проверка - нельзя создать двух одинаковых курьеров")
    public void createIdenticalUser() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        statusCode = response.then().log().all().extract().statusCode();
        String message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(403));
        assertThat(message, equalTo("User already exists"));
        assertThat(isCreate, equalTo(false));
    }
    @Test
    @DisplayName("Создание пользователя без почты")
    @Description("Проверка - нельзя создать пользователя без почты (пустое значение)")
    public void createUserWithoutEmail() {
        Response response = StepUser.createUser("", RANDOM_PASSWORD, RANDOM_NAME);
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        String message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");

        assertThat(statusCode, equalTo(403));
        assertThat(message, equalTo("Email, password and name are required fields"));
        assertThat(isCreate, equalTo(false));
    }
    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка - нельзя создать пользователя без пароля (пустое значение)")
    public void createUserWithoutPassword() {
        Response response = StepUser.createUser(RANDOM_EMAIL, "", RANDOM_NAME);
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        String message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");

        assertThat(statusCode, equalTo(403));
        assertThat(message, equalTo("Email, password and name are required fields"));
        assertThat(isCreate, equalTo(false));
    }
    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверка - нельзя создать пользователя без имени (пустое значение)")
    public void createUserWithoutName() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, "");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        String message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");

        assertThat(statusCode, equalTo(403));
        assertThat(message, equalTo("Email, password and name are required fields"));
        assertThat(isCreate, equalTo(false));
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken !=null) {
            StepUser.deleteUser(accessToken);
        }
    }
}
