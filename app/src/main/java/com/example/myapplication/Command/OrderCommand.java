package com.example.myapplication.Command;

public class OrderCommand implements Command {
    private Orders foodOrder;

    public OrderCommand(Orders foodOrder) {
        this.foodOrder = foodOrder;
    }

    @Override
    public String execute() {
        foodOrder.makeDeliver();
        String message = "Order has been successfully delivered";
        return message;
    }
}
