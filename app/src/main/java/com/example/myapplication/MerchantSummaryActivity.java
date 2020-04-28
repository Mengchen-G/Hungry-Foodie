package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MerchantSummaryActivity extends AppCompatActivity {
    private static final String TAG = "M summary activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_summary);

        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;

        TextView summTextView = (TextView) findViewById(R.id.mSummaryTextView);
        String message = "Notified DeliveryMan";
        summTextView.setText(message);

        Button backBtn = (Button) findViewById(R.id.backMainBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startIntent.putExtra("current_client",  current_client);
            startActivity(startIntent);
            }
        });
    }
}
