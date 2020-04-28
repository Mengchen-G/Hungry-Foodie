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

import com.example.myapplication.Abfactory.Meal;
import com.example.myapplication.Abfactory.Order;

import java.util.ArrayList;

// Adapter pattern to adapt list to view
public class OrderSummaryAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;
    private static final String TAG = "OrderListAdapter";
    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public OrderSummaryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
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
        int quantity =  getItem(position).getQuantity();
        double entree_price =  meal.getEntree().getPrice();
        double drink_price =  meal.getDrink().getPrice();
        double price = entree_price + drink_price;
        String sPrice = String.valueOf(price);
        String sQuantity = String.valueOf(quantity);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, null);

        TextView txtMealName = (TextView) convertView.findViewById(R.id.cart_meal_name);
        TextView txtMealPrice = (TextView) convertView.findViewById(R.id.cart_meal_price);
        txtMealName.setText(entreeName + " meal");
        txtMealPrice.setText("$" + sPrice);

        TextView costTextView = (TextView) convertView.findViewById(R.id.costTextView);
        EditText quantityEditText = (EditText) convertView.findViewById(R.id.quantityEditText);
//        int quant = Integer.parseInt(quantityEditText.getText().toString());
//        int totalCost = quant*price
//        costTextView.setText(totalCost+"");
        costTextView.setText(quantityEditText.getText());

        return convertView;
    }
}