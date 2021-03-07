package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText uEmail, uUsername, uPassword;
    Button uRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference fStrore;
    String userID;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Fill the User information
        uEmail = findViewById(R.id.input_UEmailR);
        uUsername = findViewById(R.id.input_UnameR);
        uPassword = findViewById(R.id.input_UPwdR);
        uRegisterBtn = findViewById(R.id.btn_Register);
        progressBar = findViewById(R.id.progressBar);

        // Connect to DB --> Fire base
        fAuth = FirebaseAuth.getInstance();
        ref = database.getReference();

        // User already exist
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        // Register User
        uRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = uEmail.getText().toString().trim();
                String pwd = uPassword.getText().toString().trim();
                final String userName = uUsername.getText().toString();


                // Check email & password
                if(TextUtils.isEmpty(email)){
                    uEmail.setError("Email is required !");
                    return ;
                }
                if(TextUtils.isEmpty((pwd))){
                    uPassword.setError("Password is required !");
                    return;
                }
                if(pwd.length() < 6){
                    uPassword.setError("Password must contain 6 or more characters !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in to fire base
                fAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();

                            DatabaseReference usersRef = ref.child("users");
                            // Create data
                            final Map<String,Object> user = new HashMap<>();
                            user.put("uName", userName);
                            user.put("uEmail", email);

                            // Store data to DB

                            usersRef.setValue(user);

                            // Go to login activity
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }else{
                            Toast.makeText(Register.this, "Error !" + task.getException(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}
