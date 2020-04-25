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

public class MerchantLoginActivity extends AppCompatActivity {
    private static final String TAG = "Merchant Login";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textResponse;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_ref = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_merchant);

        editTextEmail = findViewById(R.id.login_email2);
        editTextPassword = findViewById(R.id.login_password2);
        textResponse = findViewById(R.id.response2);

        Button mNextBtn = (Button)findViewById(R.id.mNextBtn);
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
                                    Log.d(TAG, documentSnapshot.getString("name") + documentSnapshot.getString("email") + documentSnapshot.getString("password"));
                                    User user = new User(documentSnapshot.getString("name"), documentSnapshot.getString("email"), documentSnapshot.getString("password"));
                                    Intent startIntent = new Intent(getApplicationContext(), MerchantMenuActivity.class);
                                    startIntent.putExtra("current_client",  user);
                                    startActivity(startIntent);
                                }else{
                                    Toast.makeText(MerchantLoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                    textResponse.setText("Wrong password!");
                                    textResponse.setVisibility(View.VISIBLE);
                                }

                            }else{
                                Toast.makeText(MerchantLoginActivity.this, "Email does not exist!", Toast.LENGTH_LONG).show();
                                textResponse.setText("Email does not exist!");
                                textResponse.setVisibility(View.VISIBLE);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MerchantLoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, e.toString());
                }
            });
            }
        });
    }
}
