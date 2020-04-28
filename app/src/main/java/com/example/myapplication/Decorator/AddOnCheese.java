package com.example.myapplication.Decorator;

import com.example.myapplication.abfactory.Entree;

public class AddOnCheese extends AddOn{
    double price;
    Entree entree;

    public AddOnCheese(Entree entree){
        this.entree = entree;
    }

    @Override
    public String getName() {
        return entree.getName();
    }

    @Override
    public String getDescription() {
        return entree.getDescription() + " Extra cheese";
    }

    @Override
    public double getPrice() {
        return entree.getPrice() + 0.5;
    }

    public void setPrice(double p){
        this.price = p;
    }
}