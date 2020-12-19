package com.example.udclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loadData();


    }

    public void logInApp(View view) {
        if (!email.getText().toString().replace(" ", "").isEmpty()) {
            //if(password.getText().toString().equals("razdwatrzy")) {                                //haslo do pobrania
                intent = new Intent(this, Navi_Drawer.class);
                intent.putExtra("LOGIN_NAME", "fayer");
                intent.putExtra("EMAIL", email.getText().toString());
                saveData(email.getText().toString());

                startActivity(intent);
           // }
        }
    }

    public void goToRegistration(View view) {

        intent = new Intent(this, RegistrationActivity.class);
        if (!email.getText().toString().replace(" ", "").isEmpty()) {
            intent.putExtra("EMAIL", email.getText().toString());
        }
        startActivity(intent);

    }

    private void saveData(String spEmail) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY", spEmail);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String spEmail = sharedPreferences.getString("EMAIL_KEY", "");
        if (spEmail != "") {
            email.setText(spEmail);
        }
    }

    private void showToast(String text){
            //TODO trzeba dodac wyswietlanie toasta ze nie moze byc nic puste najlepij
    }
}