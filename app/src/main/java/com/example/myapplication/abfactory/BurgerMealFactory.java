package com.example.myapplication.abfactory;

public class BurgerMealFactory implements MealFactory {

    @Override
    public Drink createDrink(){
        return new Pepsi();
    }

    @Override
    public Entree createEntree() {
        return new Burger();
    }
}
