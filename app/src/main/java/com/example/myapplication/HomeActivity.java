package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle data = getIntent().getExtras();
        final User current_client = (User) data.getParcelable("current_client");
        Log.d(TAG, "name: "  + current_client.getName() + " email: " + current_client.getEmail() + " password: " + current_client.getPassword());

        String username = "Hi, " + current_client.getName();
        TextView user_welcome = findViewById(R.id.home_welcome);
        user_welcome.setText(username);
        user_welcome.setVisibility(View.VISIBLE);


        Button restBtn1 = (Button)findViewById(R.id.restBtn1);
        restBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });

        Button restBtn2 = (Button)findViewById(R.id.restBtn2);
        restBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Menu2Activity.class);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });
    }
}
