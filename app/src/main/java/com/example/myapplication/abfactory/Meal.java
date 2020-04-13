package com.example.myapplication.abfactory;

public abstract class Meal {
    Entree entree;
    Drink drink;

    Entree getEntree(){
        return entree;
    }

    Drink getDrink(){
        return drink;
    }
    abstract void prepare();
}
