package com.example.myapplication;
import com.example.myapplication.Abfactory.AmericanRestaurant;
import com.example.myapplication.Abfactory.Meal;
import com.example.myapplication.Abfactory.MexicanRestaurant;
import com.example.myapplication.Abfactory.Order;
import com.example.myapplication.Abfactory.Restaurant;
import com.example.myapplication.Strategy.*;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");
    private static final String TAG = "Option activity";
    private Map<String, Object> order;
    private TextView total_costText;
    private TextView delivery_costText;
    private Map<String, Map<String, Object>> cart_info = new HashMap<>();
    private ArrayList<Order> orderList = new ArrayList<>();
    private Restaurant rest1;
    private VIPBehavior vipBehavior = new VIPBehavior();
    private NonVIPBehavior nonVIPBehavior = new NonVIPBehavior();
    private String option;
    Client n_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Log.d(TAG, "onCreate: Started.");
        //ger user info
        Bundle data = getIntent().getExtras();

        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;
        String username = "Hi, " + current_client.getName();

        //get HashMap
        order = (Map<String, Object>) data.getSerializable("HashMap");
        //get option
        option = data.getString("Option");
        //get restaurant info
        final String restaurant = data.getString("Restaurant");

        //use abstract factory pattern to get meal object
        if(restaurant.equals("AmericanRestaurant")){
            rest1 = new AmericanRestaurant();
        }else if(restaurant.equals("MexicanRestaurant")) {
            rest1 = new MexicanRestaurant();
        }


        double total_cost = 0;
        cart_info = (Map<String, Map<String, Object>>) order.get("Orders");
        // get displayed array
//        for(int i = 0; i< cart_info.size(); i++){
//            String Meal_name = String.valueOf(((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Item name"));
//            if (orderList.size()==0){
//                Meal new_meal = rest1.createMeal(Meal_name);
//                Order new_order = new Order(new_meal, 1);
//                orderList.add(new_order);
//            }else {
//                boolean change = false;
//                for (int j = 0; j < orderList.size(); j++) {
//                    if (orderList.get(j).getMeal().getEntree().getName().equals(Meal_name)) {
//                        orderList.get(j).setQuantity(orderList.get(j).getQuantity() + 1);
//                        change = true;
//                    }
//                }
//                if(!change){
//                    Meal new_meal = rest1.createMeal(Meal_name);
//                    Order new_order = new Order(new_meal, 1);
//                    orderList.add(new_order);
//                }
//            }
//        }
        for(int i = 0; i< cart_info.size(); i++){
            String Meal_name = String.valueOf(((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Item name"));
            String meal_description = String.valueOf(((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Description"));
            if (orderList.size()==0){
                Meal new_meal = rest1.createMeal(Meal_name);
                Order new_order = new Order(new_meal, 1);
                new_order.getMeal().getEntree().setDescription(meal_description);
                new_order.getMeal().getEntree().setPrice((double) ((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Price") - new_order.getMeal().getDrink().getPrice());
                orderList.add(new_order);
            }else {
                boolean change = false;
                for (int j = 0; j < orderList.size(); j++) {
                    if (orderList.get(j).getMeal().getEntree().getName().equals(Meal_name) && orderList.get(j).getMeal().getEntree().getDescription().equals(meal_description)) {
                        orderList.get(j).setQuantity(orderList.get(j).getQuantity() + 1);
                        change = true;
                    }
                }
                if(!change){
                    Meal new_meal = rest1.createMeal(Meal_name);
                    Order new_order = new Order(new_meal, 1);
                    new_order.getMeal().getEntree().setDescription(meal_description);
                    new_order.getMeal().getEntree().setPrice((double) ((Map<String, Object>) cart_info.get(String.valueOf(i))).get("Price") - new_order.getMeal().getDrink().getPrice());
                    orderList.add(new_order);
                }
            }
        }

        //caculate total cost
        for (int i = 0; i < orderList.size(); i++){
            total_cost += orderList.get(i).getMeal().getEntree().getPrice() * orderList.get(i).getQuantity();
            total_cost += orderList.get(i).getMeal().getDrink().getPrice() * orderList.get(i).getQuantity();
        }
        //add shipping fee
        if (current_client.getType().equals("VIPCustomer") ){
            n_client = new Client(current_client.getEmail(), current_client.getName(), vipBehavior);
        }else{
            n_client = new Client(current_client.getEmail(), current_client.getName(), nonVIPBehavior);
        }
        double deliver_cost = n_client.paydeliveryFee(total_cost);
        total_cost += deliver_cost;

        delivery_costText = findViewById(R.id.delivery_cost);
        delivery_costText.setText(String.valueOf(deliver_cost));
        total_costText = findViewById(R.id.total_cost);
        total_costText.setText(String.valueOf(total_cost));

        ListView mListView = (ListView) findViewById(R.id.cart_list);
        OrderListAdapter adapter = new OrderListAdapter(this, R.layout.cart_items_layout, orderList);
        mListView.setAdapter(adapter);

        final CollectionReference db_a_restaurant = db.collection(restaurant);

        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(option.equals("Delivery")){
                    sendOrder(current_client, db_a_restaurant, "Delivery");
                    Intent startIntent = new Intent(getApplicationContext(), DeliverActivity.class);
                    startActivity(startIntent);
                }else{
                    sendOrder(current_client, db_a_restaurant, "Take out");
                    Intent startIntent = new Intent(getApplicationContext(), TakeoutActivity.class);
                    startActivity(startIntent);
                }


            }
        });

    }
    public void sendOrder(User current_client, CollectionReference db_a_restaurant, String order_option) {
        String pattern = "MM-dd-yyyy HH:mm:ss";
        Date currentTime = Calendar.getInstance().getTime();
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date according to the chosen pattern
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(currentTime);

        order.put("Time", todayAsString);
        order.put("Option", order_option);

        //save data in the restaurant collection
        db_a_restaurant.document(todayAsString).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ReviewActivity.this, "order saved", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReviewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.w(TAG, e.toString());

            }
        });
        //delete items in the cart
        users_ref.document(current_client.getEmail()).update("cart", null);

    }


}
