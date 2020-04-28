package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.abfactory.Order;
import com.example.myapplication.command.Command;
import com.example.myapplication.command.OrderCommand;
import com.example.myapplication.command.OrderInvoker;
import com.example.myapplication.command.Orders;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MakeDeliverActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference driver_ref = db.collection("Driver");
    private static final String TAG = "Order delivery activity";

    private ArrayList<String> delivery_log = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_deliver);

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;

        //load order
        try {
            delivery_log = loadOrders(driver_ref);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String order = "";
        for(int i = 0; i< delivery_log.size(); i++) {
            order += delivery_log.get(i);
            order  += "\n";
        }

        Orders foodOrder = new Orders(); // receiver
        Command orderCommand = new OrderCommand(foodOrder); // concrete command

        OrderInvoker orderInvoker = new OrderInvoker(orderCommand); // invoker
        String message = orderInvoker.invoke();


        TextView deliveryStatusTextView = (TextView) findViewById(R.id.dmdTextView);
        deliveryStatusTextView.setText(message);

        Button bBtn = (Button) findViewById(R.id.dBackBtn);
        bBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), DriverNoticeActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });

    }

    public ArrayList<String> loadOrders(CollectionReference restaurant_ref) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = restaurant_ref.get();
        ArrayList<String> orders = new ArrayList<>();
        Log.d(TAG, "Loading data......");

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
                order = String.valueOf(info.get("Driver"));
                orders.add(order);
                index += 1;
            }
        } else {
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
        }
        return orders;
    }
}
