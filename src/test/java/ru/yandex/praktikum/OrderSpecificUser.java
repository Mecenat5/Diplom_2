package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.praktikum.Constants.URL;
import static ru.yandex.praktikum.GenerateUser.*;
import static ru.yandex.praktikum.StepUser.loginUser;

public class OrderSpecificUser {
    String accessToken;
    int statusCode;
    boolean isGet;
    String message;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Получение заказа c авторизацией и с ингредиентами")
    @Description("Проверка - успешное получение заказа с авторизацией и с добавлением ингредиентов")
    public void getOrderWithAuthorizationWithIngredients() {
        Response response = StepUser.createUser(RANDOM_EMAIL_SIXTH, RANDOM_PASSWORD, RANDOM_NAME);
        loginUser(RANDOM_EMAIL_SIXTH, RANDOM_PASSWORD, " ");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        isGet = response.then().log().all().extract().path("success");
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id());
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        response = StepOrder.getOrderWithAccessToken(accessToken);
        statusCode = response.then().log().all().extract().statusCode();
        isGet = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(200));
        assertThat(isGet, equalTo(true));
    }
    @Test
    @DisplayName("Получение заказа без авторизации и с ингредиентами")
    @Description("Проверка - нельзя получить заказ без авторизации, но с добавлением ингредиентов")
    public void getOrderWithoutAuthorizationWithIngredients() {
        Response response = StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        loginUser(RANDOM_EMAIL, RANDOM_PASSWORD, " ");
        accessToken = response.then().log().all().extract().path("accessToken");
        statusCode = response.then().log().all().extract().statusCode();
        isGet = response.then().log().all().extract().path("success");
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id());
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        response = StepOrder.getOrderWithoutAccessToken();
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isGet = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(401));
        assertThat(message, equalTo("You should be authorised"));
        assertThat(isGet, equalTo(false));
    }
    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken !=null) {
            StepUser.deleteUser(accessToken);
        }
    }
}
