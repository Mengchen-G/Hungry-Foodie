package com.example.myapplication.strategy;

public class Client {
    private String email, name, type;
    public Behavior behavior;

    public Client(String email, String name, Behavior behavior){
        this.email = email;
        this.name = name;

        this.behavior = behavior;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type;
    }
    public double paydeliveryFee(double total_payment){
        return behavior.deliveryFee(total_payment);
    }
}
