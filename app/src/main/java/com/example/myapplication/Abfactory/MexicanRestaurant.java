package com.example.myapplication.Abfactory;

public class MexicanRestaurant extends Restaurant{
    public MealFactory mealFactory;

    public Meal createMeal(String item){

        if(item.equals("Chips")){
            mealFactory = new ChipsMealFactory();
            return new ChipsMeal(mealFactory);
        }else if (item.equals("Tacos")){
            mealFactory = new TacosMealFactory();
            return new TacosMeal(mealFactory);
        }
        return null;
    }
}
