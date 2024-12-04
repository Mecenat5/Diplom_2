package ru.yandex.praktikum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Constants {
    public static final String URL = "https://stellarburgers.nomoreparties.site/";
    public static final String CREATE_USER = "api/auth/register";
    public static final String LOGIN_USER = "api/auth/login";
    public static final String AUTH_USER = "api/auth/user";
    public static final String CREATE_ORDER = "api/orders";
    public static final String INGREDIENTS = "/api/ingredients/";

    public static RequestSpecification spec() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .log()
                .all();
    }
}
