package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register Activity";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TYPE = "type";
    private static final String KEY_CART = "cart";
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Spinner spinnerType;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.register_email);
        editTextName = findViewById(R.id.register_name);
        editTextPassword = findViewById(R.id.register_password);
        spinnerType = findViewById(R.id.typeSpinner);

        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        Button registerBtn = (Button)findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String type = spinnerType.getSelectedItem().toString().trim();

                CollectionReference db_user_info = db.collection("users");

                Map<String, Object> user = new HashMap<>();
                user.put(KEY_NAME, name);
                user.put(KEY_EMAIL, email);
                user.put(KEY_PASSWORD, password);
                user.put(KEY_TYPE, type);
                user.put(KEY_CART, null);

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
    public class SpinnerActivity extends Activity implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            parent.getItemAtPosition(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
            System.out.println("Default type: Customer");
        }
    }
}
