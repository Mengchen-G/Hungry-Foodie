package com.example.myapplication.Abfactory;

public class BurgerMeal extends Meal{
    MealFactory mealFactory;

    public BurgerMeal(MealFactory mealFactory){
        this.mealFactory = mealFactory;
        prepare();
    }

    public void prepare(){
        drink = mealFactory.createDrink();
        entree = mealFactory.createEntree();
    }
}
