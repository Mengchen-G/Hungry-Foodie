package com.example.myapplication.Abfactory;

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
