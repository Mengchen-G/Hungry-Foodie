package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.abfactory.Entree;
import com.example.myapplication.abfactory.Meal;
import com.example.myapplication.abfactory.Order;

import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.List;


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
        String entreeName = meal.getEntree().getName();
        String drinkName = meal.getDrink().getName();
        int quantity =  getItem(position).getQuantity();
        double entree_price =  meal.getEntree().getPrice();
        double drink_price =  meal.getDrink().getPrice();
        double price = entree_price + drink_price;
        String sPrice = String.valueOf(price);
        String sEntreePrice = String.valueOf(entree_price);
        String sDrinkPrice = String.valueOf(drink_price);
        String sQuantity = String.valueOf(quantity);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, null);

//        EditText txtMealQuantity = (EditText) convertView.findViewById(R.id.cart_meal_quantity);
        TextView txtMealQuantity = (TextView) convertView.findViewById(R.id.cart_meal_quantity);
        TextView txtMealName = (TextView) convertView.findViewById(R.id.cart_meal_name);
        TextView txtMealPrice = (TextView) convertView.findViewById(R.id.cart_meal_prince);
        TextView txtEntreeName = (TextView) convertView.findViewById(R.id.cart_entree_name);
        TextView txtEntreePrice = (TextView) convertView.findViewById(R.id.cart_entree_price);
        TextView txtDrinkName = (TextView) convertView.findViewById(R.id.cart_drink_name);
        TextView txtDrinkPrice = (TextView) convertView.findViewById(R.id.cart_drink_price);

        txtMealQuantity.setText(sQuantity);
        txtMealName.setText(entreeName + " meal");
        txtMealPrice.setText("$" + sPrice);
        txtEntreeName.setText(entreeName);
        txtEntreePrice.setText("$" + sEntreePrice);
        txtDrinkName.setText(drinkName);
        txtDrinkPrice.setText("$" + sDrinkPrice);

        return convertView;
    }
}
