package com.example.myapplication.abfactory;

public class TacosMeal extends Meal{
    MealFactory mealFactory;

    public TacosMeal(MealFactory mealFactory){
        this.mealFactory = mealFactory;
        prepare();
    }

    public void prepare(){
        drink = mealFactory.createDrink();
        entree = mealFactory.createEntree();
    }
}
