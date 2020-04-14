package com.example.myapplication.abfactory;

public class Order {
    private Meal meal;
    private int quantity;

    public Order(Meal meal, int quantity) {
        this.meal = meal;
        this.quantity = quantity;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Meal getMeal() {
        return meal;
    }

    public int getQuantity() {
        return quantity;
    }
}
