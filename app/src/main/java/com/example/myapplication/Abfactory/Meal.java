package com.example.myapplication.Abfactory;

public abstract class Meal {
    protected Entree entree;
    protected Drink drink;

    public Entree getEntree(){
        return entree;
    }

    public Drink getDrink(){
        return drink;
    }
    public abstract void prepare();
}
