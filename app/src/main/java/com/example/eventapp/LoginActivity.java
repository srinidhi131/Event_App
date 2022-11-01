package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.*;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private TextView error;
    Button Login_btn;
    private EditText password;
    private TextView regLink;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_activity);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        Login_btn = findViewById(R.id.login_btn);
        error = findViewById(R.id.error_message);
        regLink = findViewById(R.id.register_link);
        mAuth = FirebaseAuth.getInstance();

        Login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Boolean email_res = checkEmail(email.getText());
                if (!emailEntered(email.getText())){
                    email.setError("Please enter an email");
                }
                if (!checkPassword(password.getText())){
                    password.setError("Password needs at least 6 characters");
                }
                else if(!email_res){
                    error.setText("Invalid Credentials");
                }
                else{
                    error.setText("");
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(LoginActivity.this, HomePage.class));
                                    } else {
                                        error.setText("Login Failed! Try Again");
                                    }
                                }
                            });
                }
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    };


    public Boolean checkEmail(Editable email){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public Boolean checkPassword(Editable pass){
        if (pass.length() < 6){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    public Boolean emailEntered(Editable data){
        if (data.length() == 0){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
}