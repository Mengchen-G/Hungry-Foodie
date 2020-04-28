package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.myapplication.Observer.ConcreteObserver;
import java.util.Random;

// Use observer pattern to update deliver status for customer
public class DeliverActivity extends AppCompatActivity {

    private ConcreteObserver deliveryObserver = new ConcreteObserver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);

        Random rand = new Random();

        int deliveryTime = rand.nextInt(20)+25;
        deliveryObserver.updateDeliveryStatus("Food will arrive "+deliveryTime+" minutes.");
        String message = deliveryObserver.displayDeliveryStatus();

        TextView deliveryStatusTextView = (TextView) findViewById(R.id.deliveryStatusTextView);

        deliveryStatusTextView.setText(message);
    }
}
