package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;


import java.util.ArrayList;

public class PlaceAnOrder extends AppCompatActivity {

//    private Button NextProcessBtn;
//    private TextView txtTotalAmount;


    private static final String TAG = "PlaceAnOrder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_checkout);

        Log.d(TAG, "onCreate: Started.");
        ArrayList<Order> orderList = new ArrayList<>();
        Restaurant rest1 = new AmericanRestaurant();
        //get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data
        String order_name = bundle.getString("order_name");
        //use abstract factor pattern to create a meal object
        Meal meal = rest1.createMeal(order_name);
        Order order = new Order(meal, 1);
        // create a item list
        ListView mListView = (ListView) findViewById(R.id.cart_list);
        orderList.add(order);

        //display the item list
        OrderListAdapter adapter = new OrderListAdapter(this, R.layout.cart_items_layout, orderList);
        mListView.setAdapter(adapter);

//        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
//        txtTotalAmount = (TextView) findViewById(R.id.total_price);

    }

}
