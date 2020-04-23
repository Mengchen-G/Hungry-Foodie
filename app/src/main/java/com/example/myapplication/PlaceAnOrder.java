package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;


import java.util.ArrayList;

public class PlaceAnOrder extends AppCompatActivity {


    private static final String TAG = "PlaceAnOrder";
    public ArrayList<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_checkout);

        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        final User current_client = (User) data.getParcelable("current_client");
        Log.d(TAG, "name: "  + current_client.getName() + " email: " + current_client.getEmail() + " password: " + current_client.getPassword());

        String username = "Hi, " + current_client.getName();
        TextView user_welcome = findViewById(R.id.checkout_welcome);
        user_welcome.setText(username);
        user_welcome.setVisibility(View.VISIBLE);

//        ArrayList<Order> orderList = new ArrayList<>();
        Restaurant rest1 = new AmericanRestaurant();

        //get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String order_name = bundle.getString("order_name");
        //use abstract factory pattern to get meal object
        Meal meal = rest1.createMeal(order_name);
        Order order = new Order(meal, 1);
        orderList.add(order);
//        setOrderList(orderList);
        System.out.println((orderList));

        ListView mListView = (ListView) findViewById(R.id.cart_list);
        OrderListAdapter adapter = new OrderListAdapter(this, R.layout.cart_items_layout, orderList);
        mListView.setAdapter(adapter);

        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SummaryActivity.class);
                startActivity(startIntent);
            }
        });
    }
//    public void setOrderList ( ArrayList orderList )
//    {
//        this.orderList = orderList;
//    }
//
//    public ArrayList getOrderList() {
//        return orderList;
//    }
}



