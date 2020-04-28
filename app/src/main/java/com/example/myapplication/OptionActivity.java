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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

// choose to take out or eliver
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

        final Bundle extras = new Bundle();
        extras.putSerializable("HashMap", (Serializable) order);

        Button deliverBtn = (Button) findViewById(R.id.optionDBtn);
        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startIntent.putExtras(extras);
                startIntent.putExtra("Restaurant", restaurant);
                startIntent.putExtra("Option", "Delivery");
                startActivity(startIntent);
            }
        });

        Button takeoutBtn = (Button) findViewById(R.id.optionTBtn);
        takeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startIntent.putExtras(extras);
                startIntent.putExtra("Restaurant", restaurant);
                startIntent.putExtra("Option", "TakeOut");
                startActivity(startIntent);
            }
        });


//        Button deliverBtn = (Button) findViewById(R.id.optionDBtn);
//        deliverBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendOrder(current_client, db_a_restaurant, "Delivery");
//                Intent startIntent = new Intent(getApplicationContext(), DeliverActivity.class);
//                startActivity(startIntent);
//            }
//        });
//
//        Button takeoutBtn = (Button) findViewById(R.id.optionTBtn);
//        takeoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendOrder(current_client, db_a_restaurant, "Take out");
//                Intent startIntent = new Intent(getApplicationContext(), TakeoutActivity.class);
//                startActivity(startIntent);
//            }
//        });
    }








}
