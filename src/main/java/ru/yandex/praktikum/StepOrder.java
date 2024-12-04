package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class StepOrder extends Constants {
    @Step("Создание заказа с авторизацией")
    public static Response createOrderWithAccessToken(ParametersOrder parametersOrder, String accessToken) {
        return spec()
                .header("authorization", accessToken)
                .body(parametersOrder)
                .when()
                .post(CREATE_ORDER);

    }
    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAccessToken(ParametersOrder parametersOrder) {
        return spec()
                .body(parametersOrder)
                .when()
                .post(CREATE_ORDER);

    }
    @Step("Получение заказа с авторизацией")
    public static Response getOrderWithAccessToken(String accessToken) {
        return spec()
                .header("authorization",accessToken)
                .when()
                .get(CREATE_ORDER);
    }
    @Step("Получение заказа без авторизации")
    public static Response getOrderWithoutAccessToken() {
        return spec()
                .when()
                .get(CREATE_ORDER);
    }
    @Step("Получить все ингредиенты")
    public static ParametersIngredients getAllIngredients() {
        return spec()
                .when()
                .get(INGREDIENTS)
                .body()
                .as(ParametersIngredients.class);
    }
}
