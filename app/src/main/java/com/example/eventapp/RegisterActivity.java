package com.example.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private TextView error;
    Button Register_btn;
    private EditText password;
    private EditText name;
    private TextView loginLink;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        Register_btn = findViewById(R.id.reg_button);
        error = findViewById(R.id.error_message2);
        loginLink = findViewById(R.id.login_link);
        name = findViewById(R.id.editTextTextPersonName);
        mAuth = FirebaseAuth.getInstance();

        Register_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!checkEmail(email.getText())) {
                    email.setError("Please enter a valid email");
                }
                if (!checkPassword(password.getText())) {
                    password.setError("Password needs at least 6 characters");
                }
                if (!checkName(name.getText())) {
                    name.setError("Please enter name");
                } else {
                    error.setText("");
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString()).build();
                                    user.updateProfile(profileUpdates);
                                    startActivity(new Intent(RegisterActivity.this, HomePage.class));
                                } else {
                                    error.setText("Error Registering! Try Again");
                                }
                            }
                        });
                    }
                    }
            });

        loginLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

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
    public Boolean checkName(Editable name){
        if (name.length() == 0){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

}