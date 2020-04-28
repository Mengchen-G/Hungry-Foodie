package com.example.myapplication.Decorator;

import com.example.myapplication.abfactory.Entree;

public class AddOnMeat extends AddOn{
    double price;
    Entree entree;

    public AddOnMeat(Entree entree){
        this.entree = entree;
    }

    @Override
    public String getName() {
        return entree.getName();
    }

    @Override
    public String getDescription() {
        return entree.getDescription() + " Extra beef";
    }

    @Override
    public double getPrice() {
        return entree.getPrice() + 1.0;
    }

    public void setPrice(double p){
        this.price = p;
    }
}
