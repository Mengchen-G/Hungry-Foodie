package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textResponse;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.login_email);
        editTextPassword = findViewById(R.id.login_password);
        textResponse = findViewById(R.id.response);

        Button mLogBtn = (Button)findViewById(R.id.mLogBtn);
        mLogBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MerchantLoginActivity.class);
                startActivity(startIntent);
            }
        });

        Button dLogBtn = (Button)findViewById(R.id.dLogBtn);
        dLogBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), DeliveryLoginActivity.class);
                startActivity(startIntent);
            }
        });

        Button nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String login_email = editTextEmail.getText().toString().trim();
                final String login_password = editTextPassword.getText().toString().trim();
                //extract data from database
                users_ref.document(login_email).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    //check password
                                    if(documentSnapshot.getString("password").equals(login_password)) {
                                        Log.d(TAG, documentSnapshot.getString("name") + documentSnapshot.getString("email") + documentSnapshot.getString("password") + documentSnapshot.getString("type"));
                                        User user = new User(documentSnapshot.getString("name"), documentSnapshot.getString("email"), documentSnapshot.getString("password"), documentSnapshot.getString("type"));
//                                        if(documentSnapshot.getString("type").equals("DeliveryMan")) {
//                                            Intent startIntent = new Intent(getApplicationContext(), DriverNoticeActivity.class);
//                                            startIntent.putExtra("current_client", user);
//                                            startActivity(startIntent);
//                                        }
//                                        else if(documentSnapshot.getString("type").equals("Merchant")) {
//                                            Intent startIntent = new Intent(getApplicationContext(), MerchantMainMenuActivity.class);
//                                            startIntent.putExtra("current_client", user);
//                                            startActivity(startIntent);
//                                        }
                                        if(documentSnapshot.getString("type").equals("DeliveryMan")) {
                                            Intent startIntent = new Intent(LoginActivity.this, Pop_loginT.class);
                                            startActivity(startIntent);
                                        }
                                        else if(documentSnapshot.getString("type").equals("Merchant")) {
                                            Intent startIntent = new Intent(LoginActivity.this, Pop_loginT.class);
                                            startActivity(startIntent);
                                        }
                                        else {
                                            Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startIntent.putExtra("current_client", user);
                                            startActivity(startIntent);
                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                        textResponse.setText("Wrong password!");
                                        textResponse.setVisibility(View.VISIBLE);
                                    }

                                }else{
                                    Toast.makeText(LoginActivity.this, "Email does not exist!", Toast.LENGTH_LONG).show();
                                    textResponse.setText("Email does not exist!");
                                    textResponse.setVisibility(View.VISIBLE);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        Log.d(TAG, e.toString());


                    }
                });

            }
        });

    }

}