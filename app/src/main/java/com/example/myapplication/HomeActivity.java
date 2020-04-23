package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private String username;
    private static final String TAG = "Home";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle data = getIntent().getExtras();
        User current_client = (User) data.getParcelable("current_client");
        Log.d(TAG, "name: "  + current_client.getName() + " email: " + current_client.getEmail() + " password: " + current_client.getPassword());

//        Intent in = getIntent();
//        User current_client = (User) in.getParcelableExtra("current_client");

        Button restBtn1 = (Button)findViewById(R.id.restBtn1);
        restBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(startIntent);
            }
        });

        Button restBtn2 = (Button)findViewById(R.id.restBtn2);
        restBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Menu2Activity.class);
                startActivity(startIntent);
            }
        });
    }
}
