package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class OptionActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");
    private static final String TAG = "Option activity";
    private Map<String, Object> order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        Log.d(TAG, "onCreate: Started.");
        //ger user info
        Bundle data = getIntent().getExtras();

        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;
        String username = "Hi, " + current_client.getName();

        //get HashMap
        order = (Map<String, Object>) data.getSerializable("HashMap");

        //get restaurant info
        final String restaurant = data.getString("Restaurant");

        final CollectionReference db_a_restaurant = db.collection(restaurant);

        Button deliverBtn = (Button) findViewById(R.id.optionDBtn);
        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder(current_client, db_a_restaurant, "Delivery");
                Intent startIntent = new Intent(getApplicationContext(), DeliverActivity.class);
                startActivity(startIntent);
            }
        });

        Button takeoutBtn = (Button) findViewById(R.id.optionTBtn);
        takeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder(current_client, db_a_restaurant, "Take out");
                Intent startIntent = new Intent(getApplicationContext(), TakeoutActivity.class);
                startActivity(startIntent);
            }
        });
    }

    public void sendOrder(User current_client, CollectionReference db_a_restaurant, String order_option) {
        String pattern = "MM-dd-yyyy HH:mm:ss";
        Date currentTime = Calendar.getInstance().getTime();
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date according to the chosen pattern
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(currentTime);

        order.put("Time", todayAsString);
        order.put("Option", order_option);

        //save data in the restaurant collection
        db_a_restaurant.document(todayAsString).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(OptionActivity.this, "order saved", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.w(TAG, e.toString());

            }
        });

        //delete items in the cart
        users_ref.document(current_client.getEmail()).update("cart", null);

    }
}
