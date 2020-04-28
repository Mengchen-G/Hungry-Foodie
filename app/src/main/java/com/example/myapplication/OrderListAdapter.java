package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Abfactory.Meal;
import com.example.myapplication.Abfactory.Order;

import java.util.ArrayList;


public class OrderListAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;
    private static final String TAG = "OrderListAdapter";
    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public OrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView: Started.");
        //get the information
        Meal meal = getItem(position).getMeal();
        int quantity = getItem(position).getQuantity();
        String entreeName = meal.getEntree().getName();
        String drinkName = meal.getDrink().getName();
        double entree_price =  meal.getEntree().getPrice();
        double drink_price =  meal.getDrink().getPrice();
        double price = entree_price * quantity + drink_price * quantity;
        String sPrice = String.valueOf(price);
        String sEntreePrice = String.valueOf(entree_price);
        String sDrinkPrice = String.valueOf(drink_price);
        String sQuantity = String.valueOf(quantity);
        String description = meal.getEntree().getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, null);

//        EditText txtMealQuantity = (EditText) convertView.findViewById(R.id.cart_meal_quantity);
//        int quant = Integer.parseInt(txtMealQuantity.getText().toString());
//        TextView txtMealQuantity = (TextView) convertView.findViewById(R.id.cart_meal_quantity);
        TextView txtMealName = (TextView) convertView.findViewById(R.id.cart_meal_name);
        TextView txtMealPrice = (TextView) convertView.findViewById(R.id.cart_meal_price);
        TextView txtEntreeName = (TextView) convertView.findViewById(R.id.cart_entree_name);
        TextView txtEntreePrice = (TextView) convertView.findViewById(R.id.cart_entree_price);
        TextView txtDrinkName = (TextView) convertView.findViewById(R.id.cart_drink_name);
        TextView txtDrinkPrice = (TextView) convertView.findViewById(R.id.cart_drink_price);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.cart_meal_description);

//        txtMealQuantity.setText(sQuantity);
        txtMealName.setText(entreeName + " meal");
        txtDescription.setText(description);
        txtMealPrice.setText("$" + sPrice);
        txtEntreeName.setText(entreeName);
        txtEntreePrice.setText("$" + sEntreePrice + " x " + sQuantity);
        txtDrinkName.setText(drinkName);
        txtDrinkPrice.setText("$" + sDrinkPrice + " x " + sQuantity);

        return convertView;
    }
}