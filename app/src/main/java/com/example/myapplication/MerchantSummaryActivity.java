package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Button checkBtn = (Button) findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MerchantSummaryActivity.this, Pop_mDelivery.class);
                startActivity(startIntent);
            }
        });

        Button backBtn = (Button) findViewById(R.id.backMainBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(), MerchantMainMenuActivity.class);
            startIntent.putExtra("current_client",  current_client);
            startActivity(startIntent);
            }
        });
    }
}
