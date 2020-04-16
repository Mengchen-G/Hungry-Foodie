package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton burgerBtn = (ImageButton)findViewById(R.id.burger_btn);
        burgerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MenuActivity.this, PlaceAnOrder.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                String order_name = "Burger";
                bundle.putString("order_name", order_name);
                //Add the bundle to the intent
                startIntent.putExtras(bundle);
                //Fire the activity
                startActivity(startIntent);
            }
        });

        ImageButton hotdogBtn = (ImageButton)findViewById(R.id.hotdog_btn);
        hotdogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MenuActivity.this, PlaceAnOrder.class);
                Bundle bundle = new Bundle();
                String order_name = "Hotdog";
                bundle.putString("order_name", order_name);
                startIntent.putExtras(bundle);
                startActivity(startIntent);
            }
        });
    }
}