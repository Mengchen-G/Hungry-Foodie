package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapplication.abfactory.Restaurant;

import java.util.ArrayList;
import java.util.Map;

public class AddOnActivity extends AppCompatActivity {
    private static final String TAG = "AddOnActivity";
    private Map<String, Object> order;
    private String email;
    private Restaurant rest1;
    private CheckBox checkbox_cheese, checkbox_meat;
    private ArrayList<String> checkBoxList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on);

        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        Log.d(TAG, current_client.getName());

        assert current_client != null;
        String username = "Hi, " + current_client.getName();
        email = current_client.getEmail();
        TextView user_welcome = findViewById(R.id.addon_welcome);
        user_welcome.setText(username);
        user_welcome.setVisibility(View.VISIBLE);

        final String restaurant = data.getString("Restaurant");
        final String order_name = data.getString("order_name");



        checkbox_cheese = (CheckBox) findViewById(R.id.cheese);
        checkbox_meat = (CheckBox) findViewById(R.id.meat);
        checkBoxList = new ArrayList<>();

        checkbox_cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_cheese.isChecked()){
                    checkBoxList.add("Cheese");
                }
            }
        });

        checkbox_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_meat.isChecked()){
                    checkBoxList.add("Meat");
                }
            }
        });

        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(AddOnActivity.this, PlaceAnOrder.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("order_name", order_name);
                bundle.putString("Restaurant", restaurant);
                bundle.putStringArrayList("CheckboxList", checkBoxList);
                //Add the bundle to the intent
                startIntent.putExtras(bundle);
                startIntent.putExtra("current_client",  current_client);
                //Fire the activity
                startActivity(startIntent);
            }
        });



    }
}
