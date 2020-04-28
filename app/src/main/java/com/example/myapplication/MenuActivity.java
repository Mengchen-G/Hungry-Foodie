package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

// menu for restaurant 1
// Meals built by Abstract factory
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "Menu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle data = getIntent().getExtras();
        final User current_client = (User) data.getParcelable("current_client");
        Log.d(TAG, "name: "  + current_client.getName() + " email: " + current_client.getEmail() + " password: " + current_client.getPassword());

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        // add greeting message based on 24-hour of the day
        String message = "";
        if(timeOfDay < 12){
            message += "Good Morning ";
        }
        else if(timeOfDay < 16){
            message += "Good Afternoon ";
        }
        else {
            message += "Good Evening ";
        }

        String username = message + current_client.getName() + "~";
        TextView user_welcome = findViewById(R.id.menu_welcome);
        user_welcome.setText(username);
        user_welcome.setVisibility(View.VISIBLE);

        ImageButton burgerBtn = (ImageButton)findViewById(R.id.burger_btn);
        burgerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MenuActivity.this, AddOnActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                String order_name = "Burger";
                bundle.putString("order_name", order_name);
                bundle.putString("Restaurant", "AmericanRestaurant");
                //Add the bundle to the intent
                startIntent.putExtras(bundle);
                startIntent.putExtra("current_client",  current_client);
                //Fire the activity
                startActivity(startIntent);
            }
        });

        ImageButton hotdogBtn = (ImageButton)findViewById(R.id.hotdog_btn);
        hotdogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MenuActivity.this, AddOnActivity.class);
                Bundle bundle = new Bundle();
                String order_name = "Hotdog";
                bundle.putString("order_name", order_name);
                bundle.putString("Restaurant", "AmericanRestaurant");
                startIntent.putExtras(bundle);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });
    }
}