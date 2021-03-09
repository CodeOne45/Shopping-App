package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText uEmail, uPassword;
    Button uLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uEmail = findViewById(R.id.input_UemailL);
        uPassword = findViewById(R.id.input_UPwdL);
        progressBar = findViewById(R.id.progressBarLogin);
        uLoginBtn = findViewById(R.id.btn_Login);

        // Acces to DB
        fAuth = FirebaseAuth.getInstance();

        // User already coonected
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CategoriesApi.class));
            finish();
        }

        uLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uEmail.getText().toString().trim();
                String pwd = uPassword.getText().toString().trim();

                // Check email & password
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty((pwd))){
                    uEmail.setError("Email is required !");
                    uPassword.setError("Password is required !");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    uEmail.setError("Email is required !");
                    return ;
                }
                if(TextUtils.isEmpty((pwd))){
                    uPassword.setError("Password is required !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate the user
                fAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login is successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CategoriesApi.class));
                        }else{
                            Toast.makeText(Login.this, "Error: Username or password was wrong!" + task.getException(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    public void Register(View view){
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }
}
