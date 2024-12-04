package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class StepUser extends Constants {

    @Step("Создание пользователя")
    public static Response createUser(String email, String password, String name) {
        ParametersUser parametersUser = new ParametersUser (email, password, name);
        return spec()
                .body(parametersUser)
                .when()
                .post(CREATE_USER);
    }

    @Step("Логин пользователя")
    public static Response loginUser(String email, String password, String name) {
        ParametersUser parametersUser = new ParametersUser (email, password, name);
        return spec()
                .body(parametersUser)
                .when()
                .post(LOGIN_USER);
    }
    @Step("Изменение данных пользователя авторизованного")
    public static Response changeUserAuthorization(String email, String password, String name, String accessToken) {
        ParametersUser parametersUser = new ParametersUser (email, password, name);
        return spec()
                .header("authorization", accessToken)
                .body(parametersUser)
                .when()
                .patch(AUTH_USER);
    }
    @Step("Изменение данных пользователя неавторизованного")
    public static Response changeUserNoAuthorization(String email, String password, String name) {
        ParametersUser parametersUser = new ParametersUser (email, password, name);
        return spec()
                .body(parametersUser)
                .when()
                .patch(AUTH_USER);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return spec()
                .delete(AUTH_USER);
    }
}

