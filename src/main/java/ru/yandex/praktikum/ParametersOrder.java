package ru.yandex.praktikum;

import java.util.List;

public class ParametersOrder {
    private List<String> ingredients;

    public ParametersOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public ParametersOrder() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
