package com.example.myapplication.Abfactory;

public class Chips extends Entree{
    String name = "Chips";
    double price = 3.0;

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double p){
        this.price = p;
    }
}
