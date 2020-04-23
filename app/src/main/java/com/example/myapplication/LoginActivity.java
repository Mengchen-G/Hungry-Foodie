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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textResponse;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.login_email);
        editTextPassword = findViewById(R.id.login_password);
        textResponse = findViewById(R.id.response);


        Button nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String login_email = editTextEmail.getText().toString().trim();
                final String login_password = editTextPassword.getText().toString().trim();

                users_ref.document(login_email).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    if(documentSnapshot.getString("password").equals(login_password)) {
                                                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(startIntent);
                                    }else{
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