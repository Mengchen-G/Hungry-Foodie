package com.example.myapplication.abfactory;

public class ChipsMeal extends Meal {
    MealFactory mealFactory;

    public ChipsMeal(MealFactory mealFactory){
        this.mealFactory = mealFactory;
        prepare();
    }

    public void prepare(){
        drink = mealFactory.createDrink();
        entree = mealFactory.createEntree();
    }

}
