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

public class DeliveryLoginActivity extends AppCompatActivity {
    private static final String TAG = "Delivery Login";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textResponse;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login);

        editTextEmail = findViewById(R.id.login_email3);
        editTextPassword = findViewById(R.id.login_password3);
        textResponse = findViewById(R.id.response3);

        Button mNextBtn = (Button)findViewById(R.id.dNextBtn);
        mNextBtn.setOnClickListener(new View.OnClickListener() {

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
                                        Intent startIntent = new Intent(getApplicationContext(), DriverNoticeActivity.class);
                                        startIntent.putExtra("current_client",  user);
                                        startActivity(startIntent);
                                    }else{
                                        Toast.makeText(DeliveryLoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                        textResponse.setText("Wrong password!");
                                        textResponse.setVisibility(View.VISIBLE);
                                    }

                                }else{
                                    Toast.makeText(DeliveryLoginActivity.this, "Email does not exist!", Toast.LENGTH_LONG).show();
                                    textResponse.setText("Email does not exist!");
                                    textResponse.setVisibility(View.VISIBLE);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DeliveryLoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        Log.d(TAG, e.toString());
                    }
                });
            }
        });
    }
}
