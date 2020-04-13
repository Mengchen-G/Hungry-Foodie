package com.example.myapplication.abfactory;

public class HotdogMealFactory implements MealFactory{
    @Override
    public Drink createDrink(){
        return new Pepsi();
    }

    @Override
    public Entree createEntree() {
        return new Hotdog();
    }
}
