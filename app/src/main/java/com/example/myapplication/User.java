package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

// user class
public class User implements Parcelable {
    private String name, email, password, type;
//    private Map<String, Object> cart;


    public User(String name, String email, String password, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
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

    public String getType() {
        return type;
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
        dest.writeString(type);
//        dest.writeSerializable((Serializable) cart);
//        dest.writeList(cart);

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
        this.type = in.readString();
//        this.cart = (Map<String, Object>) in.readSerializable();
//        this.cart = in.readArrayList(null);

    }
}
