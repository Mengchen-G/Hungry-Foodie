package com.example.myapplication.Abfactory;

public class Order {
    private Meal meal;
    private int quantity;
    private double price;



    public Order(Meal meal, int quantity) {
        this.meal = meal;
        this.quantity = quantity;
    }

    public double calPrice(){
        return meal.getEntree().getPrice() * quantity + meal.getDrink().getPrice() * quantity;
    }

    public double getPrice() {
        this.price = calPrice();
        return price;
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
