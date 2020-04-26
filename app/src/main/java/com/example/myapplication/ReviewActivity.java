package com.example.myapplication;
import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.MexicanRestaurant;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;
import com.example.myapplication.strategy.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
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

        //caculate total cost
        for (int i = 0; i < orderList.size(); i++){
            total_cost += orderList.get(i).getMeal().getEntree().getPrice() * orderList.get(i).getQuantity();
            total_cost += orderList.get(i).getMeal().getDrink().getPrice() * orderList.get(i).getQuantity();
        }
        //add shipping fee
        if (current_client.getType() == "VIPCustomer" ){
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



//        Client client = new Client(current_client.getEmail(), current_client.getName(),);

        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();
                extras.putSerializable("HashMap", (Serializable) order);

                Intent startIntent = new Intent(getApplicationContext(), OptionActivity.class);
                startIntent.putExtra("current_client",  current_client);
                startIntent.putExtras(extras);
                startIntent.putExtra("Restaurant", restaurant);
                startActivity(startIntent);
            }
        });
    }


}
