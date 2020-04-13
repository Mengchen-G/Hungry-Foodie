package com.example.myapplication.abfactory;

public class BurgerMeal extends Meal{
    MealFactory mealFactory;

    public BurgerMeal(MealFactory mealFactory){
        this.mealFactory = mealFactory;
        prepare();
    }

    void prepare(){
        drink = mealFactory.createDrink();
        entree = mealFactory.createEntree();
    }
}