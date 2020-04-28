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

public class MOrderListAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;
    private static final String TAG = "OrderListAdapter";
    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public MOrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
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
        String sQuantity = String.valueOf(quantity);
        String description = meal.getEntree().getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, null);

        TextView txtMealName = (TextView) convertView.findViewById(R.id.list_meal_name);
        TextView txtMealQuantity = (TextView) convertView.findViewById(R.id.list_meal_quantity);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.list_meal_description);

        txtMealName.setText(meal.getEntree().getName() + " meal");
        txtDescription.setText(description);
        txtMealQuantity.setText(sQuantity);

        return convertView;
    }
}
