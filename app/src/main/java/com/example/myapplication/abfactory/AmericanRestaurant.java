package com.example.myapplication.abfactory;

public class AmericanRestaurant extends Restaurant{
    public MealFactory mealFactory;

    public Meal createMeal(String item){

        if(item.equals("Burger")){
            mealFactory = new BurgerMealFactory();
            return new BurgerMeal(mealFactory);
        }else if (item.equals("Hotdog")){
            mealFactory = new HotdogMealFactory();
            return new HotdogMeal(mealFactory);
        }
        return null;
    }
}
