package com.example.myapplication;

import com.example.myapplication.abfactory.Order;

import java.util.ArrayList;

public class User {
    private String name, email, password;
    private ArrayList<Order> cart;
    private ArrayList<Order> order_history;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
