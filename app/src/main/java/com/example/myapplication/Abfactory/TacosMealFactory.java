package com.example.myapplication.Abfactory;

public class TacosMealFactory implements MealFactory {
    @Override
    public Drink createDrink(){
        return new Lemonade();
    }

    @Override
    public Entree createEntree() {
        return new Tacos();
    }
}
