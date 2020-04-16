package com.example.myapplication.observer;

import com.example.myapplication.abfactory.Order;

import java.util.ArrayList;
import java.util.List;

public class ConcreteObserver implements Observer {

    private String takeoutStatus;
    private String deliveryStatus;

    public void updateTakeoutStatus(String takeoutStatus){
        this.takeoutStatus =  takeoutStatus;
        displayTakeoutStatus();
    }

    public String displayTakeoutStatus(){
        System.out.println("************************");
        System.out.println("     Takeout Status     ");
        System.out.println(takeoutStatus);
        System.out.println("************************");
        return takeoutStatus;
    }

    public void updateDeliveryStatus(String deliveryStatus){
        this.deliveryStatus =  deliveryStatus;
        displayDeliveryStatus();
    }

    public String displayDeliveryStatus(){
        System.out.println("*************************");
        System.out.println("     Delivery Status     ");
        System.out.println(deliveryStatus);
        System.out.println("*************************");
        return deliveryStatus;
    }

}
