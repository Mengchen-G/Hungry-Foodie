package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private static final String TAG = "OrderSummary";
    public ArrayList<Order> orderSummary = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summlist);

//        orderSummary = placedOrder.getOrderList();
        Restaurant rest1 = new AmericanRestaurant();

        //use abstract factory pattern to get meal object
        Meal meal = rest1.createMeal("Burger");
        Order orders = new Order(meal, 1);
        orderSummary.add(orders);

        System.out.println("OrderList in Summary Activity:"+orderSummary);

        ListView mListView2 = (ListView) findViewById(R.id.summary_list);
        OrderSummaryAdapter adapter1 = new OrderSummaryAdapter(this, R.layout.activity_summary, orderSummary);
        mListView2.setAdapter(adapter1);

//        Button updateBtn = (Button) findViewById(R.id.updateBtn);
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText quantityEditText = (EditText) findViewById(R.id.quantityEditText);
//                TextView costView = (TextView) findViewById(R.id.costTextView);
//
//                int quant = Integer.parseInt(quantityEditText.getText().toString());
//                costView.setText(quant+"");
//            }
//        });


        Button checkoutBtn = (Button) findViewById(R.id.next_process_btn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
