package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

// functionality on merchant's end,
// able to assign order to drivers
// not able to get done: edit menu
public class MerchantMainMenuActivity extends AppCompatActivity {
    private static final String TAG = "Merchant Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main_menu);
        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;

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

        final String restaurant = data.getString("Restaurant");

        Button display_btn = (Button)findViewById(R.id.display_btn);
        display_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MerchantDisplayOrder.class);
                startIntent.putExtra("current_client",  current_client);
                startIntent.putExtra("Restaurant",  restaurant);
                startActivity(startIntent);
            }
        });

        Button adjust_btn = (Button)findViewById(R.id.adjust_menu_btn);
        adjust_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MerchantMenuActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startIntent.putExtra("Restaurant",  restaurant);
                startActivity(startIntent);
            }
        });



    }
}
