package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.abfactory.Order;
import java.util.ArrayList;

public class User implements Parcelable {
    private String name, email, password;
    private ArrayList cart;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, ArrayList<String> cart) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cart = cart;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public void setCart(ArrayList<String> cart) {
        this.cart = cart;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeList(cart);

    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    // Parcelling part
    public User(Parcel in){
        /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
        this.name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.cart = in.readArrayList(null);
    }
}
