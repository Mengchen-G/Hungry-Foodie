package com.example.myapplication.abfactory;

public class Burger extends Entree {
    String name = "Burger";
    double price = 4.0;

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
