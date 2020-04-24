package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class PlaceAnOrder extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");
    private static final String TAG = "PlaceAnOrder";
    private ArrayList<Order> orderList = new ArrayList<>();
    private Map<String, Map<String, Object>> cart_info = new HashMap<>();
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_checkout);

        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");

        assert current_client != null;
        String username = "Hi, " + current_client.getName();
        email = current_client.getEmail();
        String password = current_client.getPassword();
        TextView user_welcome = findViewById(R.id.checkout_welcome);
        user_welcome.setText(username);
        user_welcome.setVisibility(View.VISIBLE);

        Restaurant rest1 = new AmericanRestaurant();

        //get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String order_name = bundle.getString("order_name");
        //use abstract factory pattern to get meal object
        Meal meal = rest1.createMeal(order_name);
        Order order = new Order(meal, 1);

        //load order
        try {
            cart_info = loadCart(users_ref);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //add cart items to database
        cart_info = updateDatabase(order, current_client, users_ref, cart_info);

        // using for loop for iteration over Map.entrySet()
        for(int i = 0; i< cart_info.size(); i++){
            String Meal_name = String.valueOf(((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Item name"));
            if (orderList.size()==0){
                Meal new_meal = rest1.createMeal(Meal_name);
                Order new_order = new Order(new_meal, 1);
                orderList.add(new_order);
            }else {
                boolean change = false;
                for (int j = 0; j < orderList.size(); j++) {
                    if (orderList.get(j).getMeal().getEntree().getName().equals(Meal_name)) {
                        orderList.get(j).setQuantity(orderList.get(j).getQuantity() + 1);
                        change = true;
                    }
                }
                if(!change){
                    Meal new_meal = rest1.createMeal(Meal_name);
                    Order new_order = new Order(new_meal, 1);
                    orderList.add(new_order);
                }
            }
        }


        ListView mListView = (ListView) findViewById(R.id.cart_list);
        OrderListAdapter adapter = new OrderListAdapter(this, R.layout.cart_items_layout, orderList);
        mListView.setAdapter(adapter);

        Button continueBtn = (Button) findViewById(R.id.continue_shop);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });



        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users_ref.document(current_client.getEmail()).update("cart", cart_info);
                Intent startIntent = new Intent(getApplicationContext(), OptionActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });
    }

    public Map<String, Map<String, Object>> updateDatabase(Order order, User current_client, CollectionReference users_ref, Map<String, Map<String, Object>> cart_info){
        Log.d(TAG, "Update data.........");
        int cart_size = cart_info.size();
        Log.d(TAG, "update: " + cart_info.size());
        Map<String, Object> meal_info = new HashMap<>();
        meal_info.put("Item name", order.getMeal().getEntree().getName());
        meal_info.put("Quantity", order.getQuantity());
        meal_info.put("Price", order.getPrice());
        cart_info.put(String.valueOf(cart_size), meal_info);
        users_ref.document(current_client.getEmail()).update("cart", cart_info);
        Log.d(TAG, "Data updated.........");
        return cart_info;
    }

    public Map<String, Map<String, Object>> loadCart(CollectionReference users_ref) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = users_ref.get();
        Log.d(TAG, "Loading data.........");

        while(!task.isComplete()) {
            Thread.sleep(1000);
        }

        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d(TAG, document.getId() + " => " + document.getData());
                Log.d(TAG, String.valueOf(document.getData().get("cart")));
                if (document.getId().equals(email)) {
                    if (document.getData().get("cart") == null) {
                        return new HashMap<>();
                    }
                    return (Map<String, Map<String, Object>>) document.getData().get("cart");
                }
            }
        } else {
            Log.w(TAG, "Error getting documents.", task.getException());
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
        }
        return null;
    }



}



