package com.example.myapplication.abfactory;

public abstract class Entree {
    String name;
    double price;
    String description = "";

    public abstract String getName();

    public abstract double getPrice();

    public String getDescription(){
        return description;
    }

    public abstract void setPrice(double price);

    public void setDescription(String description) {
        this.description = description;
    }
}
