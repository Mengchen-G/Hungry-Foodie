package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Observer.ConcreteObserver;

import java.util.Random;

public class TakeoutActivity extends AppCompatActivity {

    private ConcreteObserver takeoutObserver = new ConcreteObserver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout);

        Random rand = new Random();

        int takeoutTime = rand.nextInt(20)+15;
        takeoutObserver.updateTakeoutStatus("Food is ready in "+takeoutTime+" minutes.");
        String message = takeoutObserver.displayTakeoutStatus();

        TextView takeoutStatusTextView = (TextView) findViewById(R.id.takeoutStatusTextView);

        takeoutStatusTextView.setText(message);
    }
}
