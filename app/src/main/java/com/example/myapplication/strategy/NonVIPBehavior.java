package com.example.myapplication.strategy;

public class NonVIPBehavior implements Behavior {

    public double deliveryFee(double total_payment){
        if (total_payment >= 25){
            return 0;
        }else{
            return 5;
        }

    }
}
