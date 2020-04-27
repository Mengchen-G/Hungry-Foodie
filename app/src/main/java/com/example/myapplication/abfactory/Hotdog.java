package com.example.myapplication.abfactory;

public class Hotdog extends Entree{
    String name = "Hotdog";
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
