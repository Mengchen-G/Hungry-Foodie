package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.myapplication.abfactory.AmericanRestaurant;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.MexicanRestaurant;
import com.example.myapplication.abfactory.Order;
import com.example.myapplication.abfactory.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MerchantDisplayOrder extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference restaurant_ref;
    private ArrayList<Order> orderList = new ArrayList<>();
    private Map<String, Map<String,Object>> cart_info = new HashMap<>();
    private static final String TAG = "M display activity";
    private Restaurant rest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_display_order);

        Log.d(TAG, "onCreate: Started.");

        Bundle data = getIntent().getExtras();
        assert data != null;
        final User current_client = (User) data.getParcelable("current_client");
        assert current_client != null;
        String username = "Hi, " + current_client.getName();
        final String restaurant = data.getString("Restaurant");
        restaurant_ref = db.collection(restaurant);

        //load order
        try {
            cart_info = loadOrders(restaurant_ref);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(restaurant.equals("AmericanRestaurant")){
            rest1 = new AmericanRestaurant();
        }else if(restaurant.equals("MexicanRestaurant")) {
            rest1 = new MexicanRestaurant();
        }


        // get displayed array
        for(int i = 0; i< cart_info.size(); i++) {
            Map<String, Object> customer_order = (Map<String, Object>)cart_info.get(String.valueOf(i));
            for(int j = 0; j< customer_order.size(); j++) {

                Meal new_meal = rest1.createMeal(String.valueOf(((Map<String, Object>)customer_order.get(String.valueOf(j))).get("Item name")));
                Order new_order = new Order(new_meal, 1);
                new_order.getMeal().getEntree().setDescription(String.valueOf(((Map<String, Object>)customer_order.get(String.valueOf(j))).get("Description")));
                orderList.add(new_order);
            }

        }

        ListView mListView = (ListView) findViewById(R.id.cart_list);
        MOrderListAdapter adapter = new MOrderListAdapter(this, R.layout.merchant_items_layout, orderList);
        mListView.setAdapter(adapter);



    }
    public Map<String, Map<String,Object>> loadOrders(CollectionReference restaurant_ref) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = restaurant_ref.get();
        Map<String, Map<String,Object>> orders = new HashMap<>();
        Log.d(TAG, "Loading data.........");

        while(!task.isComplete()) {
            Thread.sleep(1000);
        }
        int index = 0;
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d(TAG, document.getId() + " => " + document.getData());
                Map<String, Object> order = new HashMap<>();
                order = ((Map<String, Object>)document.getData().get("Orders"));
                orders.put(String.valueOf(index), order);
                index += 1;
            }
        } else {
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
        }
        return orders;
    }
}