package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

// Merchant assign deliveries to DeliveryMan
public class DriverNoticeActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference driver_ref = db.collection("Driver");
    private static final String TAG = "Driver activity";

    private ArrayList<String> notify_info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_notice);

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;

        //load order
        try {
            notify_info = loadOrders(driver_ref);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String restaurant_name = "";
        for(int i = 0; i< notify_info.size(); i++) {
            restaurant_name += notify_info.get(i);
            restaurant_name += "\n";
        }

        String message = ": Ready for delivering";

        if(restaurant_name.equals("")){
            message = "No update";
        }else{
            message = restaurant_name + message;
        }

        TextView deliveryStatusTextView = (TextView) findViewById(R.id.deliveryStatusTextView);
        deliveryStatusTextView.setText(message);

        Button pickBtn = (Button) findViewById(R.id.pickBtn);
        pickBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(), MakeDeliverActivity.class);
            startIntent.putExtra("current_client",  current_client);
            startActivity(startIntent);
            }
        });

    }

    public ArrayList<String> loadOrders(CollectionReference restaurant_ref) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = restaurant_ref.get();
        ArrayList<String> orders = new ArrayList<>();
        Log.d(TAG, "Loading data.........");

        while(!task.isComplete()) {
            Thread.sleep(1000);
        }
        int index = 0;
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d(TAG, document.getId() + " => " + document.getData());
                String order = "";
                Map<String, Object> info = new HashMap<>();
                info = document.getData();
                order = String.valueOf(info.get("Restaurant"));
                orders.add(order);
                index += 1;
            }
        } else {
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
        }
        return orders;
    }
}
