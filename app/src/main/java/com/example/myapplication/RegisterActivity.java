package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1beta1.WriteResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register Activity";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.register_email);
        editTextName = findViewById(R.id.register_name);
        editTextPassword = findViewById(R.id.register_password);



        Button registerBtn = (Button)findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                CollectionReference db_user_info = db.collection("users");
//                User user = new User(name, email, password);
                Map<String, Object> user = new HashMap<>();
                user.put(KEY_NAME, name);
                user.put(KEY_EMAIL, email);
                user.put(KEY_PASSWORD, password);

                db_user_info.document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.w(TAG, e.toString());

                    }
                });



                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
