package com.example.eventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.login_button);
        Login.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        });
    }

}