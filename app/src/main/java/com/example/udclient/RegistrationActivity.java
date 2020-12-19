package com.example.udclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email,password,nick;
    private String sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        nick = findViewById(R.id.regNick);
        Intent intent = getIntent();
        sEmail=intent.getStringExtra("EMAIL");


    }

    public void signUp(View view) {
        if(!email.getText().toString().replace(" ","").isEmpty()) {
            if(!password.getText().toString().replace(" ","").isEmpty()) {
                if(!nick.getText().toString().replace(" ","").isEmpty()) {
                    Intent intent = new Intent(this , LoginActivity.class);
                    saveData(email.getText().toString());
                    startActivity(intent);
                }
            }
        }

    }

    private void saveData (String spEmail){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY",spEmail);
        editor.apply();
    }

    private void showToast(String text){
        //TODO trzeba dodac wyswietlanie toasta ze nie moze byc nic puste najlepij
    }
}