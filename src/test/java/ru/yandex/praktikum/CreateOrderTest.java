package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.praktikum.GenerateUser.*;

public class CreateOrderTest {
    String accessToken;
    int statusCode;
    int order;
    boolean isCreate;
    String message;
    Response response;


    @Before
    public void setUp() {
        StepUser.createUser(RANDOM_EMAIL, RANDOM_PASSWORD, RANDOM_NAME);
        StepUser.loginUser(RANDOM_EMAIL, RANDOM_PASSWORD, " ");
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    @Description("Проверка - успешное создание заказа без авторизации, но с добавлением ингредиентов")
    public void createOrderWithoutAuthorizationWithIngredients() {
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id());
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        Response response = StepOrder.createOrderWithoutAccessToken(parametersOrder);
        statusCode = response.then().log().all().extract().statusCode();
        order = response.then().log().all().extract().path("order.number");
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(200));
        assertThat(order, notNullValue());
        assertThat(isCreate, equalTo(true));
    }
    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Проверка - нельзя создать заказ без авторизации и без добавления ингредиентов")
    public void createOrderWithoutAuthorizationWithoutIngredients() {
        ParametersOrder parametersOrder = new ParametersOrder();
        Response response = StepOrder.createOrderWithoutAccessToken(parametersOrder);
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Ingredient ids must be provided"));
        assertThat(isCreate, equalTo(false));
    }
    @Test
    @DisplayName("Создание заказа без авторизации и c неверным хешем ингредиентов")
    @Description("Проверка - нельзя создать заказ без авторизации, но c неверным хешем ингредиента")
    public void createOrderWithoutAuthorizationWithIncorrectHash() {
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id()+ "fgt");
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        Response response = StepOrder.createOrderWithoutAccessToken(parametersOrder);
        statusCode = response.then().log().all().extract().statusCode();
        assertThat(statusCode, equalTo(500));
    }
    @Test
    @DisplayName("Создание заказа c авторизацией и с ингредиентами")
    @Description("Проверка - успешное создание заказа с авторизацией и с добавлением ингредиентов")
    public void createOrderWithAuthorizationWithIngredients() {
        accessToken = response.then().log().all().extract().path("accessToken");
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id());
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        Response response = StepOrder.createOrderWithAccessToken(parametersOrder, accessToken);
        statusCode = response.then().log().all().extract().statusCode();
        order = response.then().log().all().extract().path("order.number");
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(200));
        assertThat(order, notNullValue());
        assertThat(isCreate, equalTo(true));
    }
    @Test
    @DisplayName("Создание заказа c авторизацией и без ингредиентов")
    @Description("Проверка - нельзя создать заказ с авторизацией, но без добавления ингредиентов")
    public void createOrderWithAuthorizationWithoutIngredients() {
        accessToken = response.then().log().all().extract().path("accessToken");
        ParametersOrder parametersOrder = new ParametersOrder();
        Response response = StepOrder.createOrderWithoutAccessToken(parametersOrder);
        statusCode = response.then().log().all().extract().statusCode();
        message = response.then().log().all().extract().path("message");
        isCreate = response.then().log().all().extract().path("success");
        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Ingredient ids must be provided"));
        assertThat(isCreate, equalTo(false));
    }
    @Test
    @DisplayName("Создание заказа с авторизацией и c неверным хешем ингредиентов")
    @Description("Проверка - нельзя создать заказ с авторизацией, но c неверным хешем ингредиента")
    public void createOrderWithAuthorizationWithIncorrectHash() {
        ParametersIngredients parametersIngredients = StepOrder.getAllIngredients();
        ArrayList<String> newIngredient = new ArrayList<>();
        newIngredient.add(parametersIngredients.getData().get(3).get_id()+ "fgt");
        newIngredient.add(parametersIngredients.getData().get(5).get_id());
        ParametersOrder parametersOrder = new ParametersOrder(newIngredient);
        Response response = StepOrder.createOrderWithoutAccessToken(parametersOrder);
        statusCode = response.then().log().all().extract().statusCode();
        assertThat(statusCode, equalTo(500));
    }
    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken !=null) {
            StepUser.deleteUser(accessToken);
        }
    }
}





