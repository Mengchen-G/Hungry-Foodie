package com.example.myapplication.abfactory;

public class HotdogMeal extends Meal{
    MealFactory mealFactory;

    public HotdogMeal(MealFactory mealFactory){
        this.mealFactory = mealFactory;
        prepare();
    }

    public void prepare(){
        drink = mealFactory.createDrink();
        entree = mealFactory.createEntree();
    }
}
