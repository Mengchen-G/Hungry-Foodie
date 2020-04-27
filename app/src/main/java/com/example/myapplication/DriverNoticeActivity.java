package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class DriverNoticeActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference driver_ref = db.collection("Driver");
    private static final String TAG = "Driver activity";

    private ArrayList<String> notify_info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_notice);

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

        String message = ": Ready to pick";

        if(restaurant_name.equals("")){
            message = "No update";
        }else{
            message = restaurant_name + ": Ready to pick";
        }


        TextView deliveryStatusTextView = (TextView) findViewById(R.id.deliveryStatusTextView);

        deliveryStatusTextView.setText(message);
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
