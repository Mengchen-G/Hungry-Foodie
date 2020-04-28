package com.example.myapplication.Abfactory;

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
