package com.example.myapplication.observer;

import com.example.myapplication.abfactory.Order;

import java.util.List;

interface Observer {
    void updateTakeoutStatus(String takeoutStatus);
//    void displayTakeoutStatus();
    void updateDeliveryStatus(String deliverStatus);
//    void displayDeliveryStatus();
}
