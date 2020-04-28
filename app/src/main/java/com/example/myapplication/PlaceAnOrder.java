package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.io.Serializable;

import com.example.myapplication.Abfactory.AmericanRestaurant;
import com.example.myapplication.Abfactory.Entree;
import com.example.myapplication.Abfactory.Meal;
import com.example.myapplication.Abfactory.MexicanRestaurant;
import com.example.myapplication.Abfactory.Order;
import com.example.myapplication.Abfactory.Restaurant;
import com.example.myapplication.Decorator.AddOnCheese;
import com.example.myapplication.Decorator.AddOnMeat;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class PlaceAnOrder extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");
    private static final String TAG = "PlaceAnOrder";
    private ArrayList<Order> orderList = new ArrayList<>();
    private Map<String, Map<String, Object>> cart_info = new HashMap<>();
    private String email;
    private Restaurant rest1;
    private Restaurant rest2;


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

        //get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String order_name = bundle.getString("order_name");
        final String restaurant = bundle.getString("Restaurant");
        ArrayList<String> checkboxList = bundle.getStringArrayList("CheckboxList");

        //use abstract factory pattern to get meal object
        if(restaurant.equals("AmericanRestaurant")){
            rest1 = new AmericanRestaurant();
            rest2 = new AmericanRestaurant();
        }else if(restaurant.equals("MexicanRestaurant")) {
            rest1 = new MexicanRestaurant();
            rest2 = new MexicanRestaurant();
        }

        Meal meal = rest1.createMeal(order_name);
        Meal meal2 = rest2.createMeal(order_name);

        Entree addon_entree = meal2.getEntree();
        for (int i = 0; i < checkboxList.size(); i++){
            if(checkboxList.get(i).equals("Meat")){
                addon_entree = new AddOnMeat(addon_entree);
            }else{
                addon_entree = new AddOnCheese(addon_entree);
            }
        }

        meal.getEntree().setDescription(addon_entree.getDescription());
        meal.getEntree().setPrice(addon_entree.getPrice());

        Log.d(TAG, String.valueOf(addon_entree.getPrice()));
        Log.d(TAG, addon_entree.getDescription());

        Order order = new Order(meal, 1);


        //load order
        try {
            cart_info = loadCart(users_ref);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //add cart items to database
        cart_info = updateDatabase(order, current_client, users_ref, cart_info);

        // get displayed array
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


        ListView mListView = (ListView) findViewById(R.id.cart_list);
        OrderListAdapter adapter = new OrderListAdapter(this, R.layout.cart_items_layout, orderList);
        mListView.setAdapter(adapter);

        Button continueBtn = (Button) findViewById(R.id.continue_shop);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = null;

                if(restaurant.equals("AmericanRestaurant")){
                    startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                }else if(restaurant.equals("MexicanRestaurant")) {
                    startIntent = new Intent(getApplicationContext(), Menu2Activity.class);
                }
                startIntent.putExtra("current_client",  current_client);
                startActivity(startIntent);
            }
        });



        Button nextBtn1 = (Button) findViewById(R.id.next_process_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerType = (String)(current_client.getType());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(customerType);
                if (customerType.equals("Customer") || customerType.equals("VIPCustomer")) {
                    //send order to the restaurant
                    CollectionReference db_a_restaurant = db.collection(restaurant);
                    Map<String, Map<String, Object> >  order_info = new HashMap<>();
                    Map<String, Object> order = new HashMap<>();
                    for(int i = 0; i < cart_info.size(); i++){
                        Map<String, Object> meal_info = new HashMap<>();
                        meal_info.put("Item name", cart_info.get(String.valueOf(i)).get("Item name"));
                        meal_info.put("Quantity", cart_info.get(String.valueOf(i)).get("Quantity"));
                        meal_info.put("Price", cart_info.get(String.valueOf(i)).get("Price"));
                        meal_info.put("Description", cart_info.get(String.valueOf(i)).get("Description"));
                        order_info.put(String.valueOf(i), meal_info);
                    }

                    order.put("Customer Email", email);
                    order.put("Orders", order_info);

                    Bundle extras = new Bundle();
                    extras.putSerializable("HashMap", (Serializable) order);

                    Intent startIntent = new Intent(getApplicationContext(), OptionActivity.class);
                    startIntent.putExtra("current_client",  current_client);
                    startIntent.putExtras(extras);
                    startIntent.putExtra("Restaurant", restaurant);
                    startActivity(startIntent);
                }
                // only allow type: customer, VIP customer to checkout
                else {
                    Intent startIntent = new Intent(PlaceAnOrder.this, Pop_mDelivery.class);
                    startActivity(startIntent);
                }
            }
        });
    }

    public Map<String, Map<String, Object>> updateDatabase(Order order, User current_client, CollectionReference users_ref, Map<String, Map<String, Object>> cart_info){
        Log.d(TAG, "Update data.........");
        int cart_size = cart_info.size();
        Map<String, Object> meal_info = new HashMap<>();
        meal_info.put("Item name", order.getMeal().getEntree().getName());
        meal_info.put("Quantity", order.getQuantity());
        meal_info.put("Price", order.getPrice());
        meal_info.put("Description", order.getMeal().getEntree().getDescription());
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



