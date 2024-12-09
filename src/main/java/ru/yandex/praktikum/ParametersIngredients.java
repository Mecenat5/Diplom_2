package ru.yandex.praktikum;

import java.util.List;

public class ParametersIngredients {
        private List<ParametersIngredient> data;
        private String success;

        public ParametersIngredients(List<ParametersIngredient> data, String success) {
            this.data = data;
            this.success = success;
        }

        public List<ParametersIngredient> getData() {
            return data;
        }

    public void setData(List<ParametersIngredient> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
