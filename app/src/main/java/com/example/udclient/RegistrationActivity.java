package com.example.udclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.udclient.classes.LoginDto;
import com.example.udclient.classes.RegisterDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email, password, nick, firstName, surName;
    private String sEmail;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.121:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        nick = findViewById(R.id.regNick);
        //firstName = findViewById(R.id.regFName);
        //surName = findViewById(R.id.regSName);
        Intent intent = getIntent();
        sEmail = intent.getStringExtra("EMAIL");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);


    }

    public void signUp(View view) {
        if (!email.getText().toString().replace(" ", "").isEmpty()) {
            if (!password.getText().toString().replace(" ", "").isEmpty()) {
                if (!nick.getText().toString().replace(" ", "").isEmpty()) {
                    //if(!firstName.getText().toString().replace(" ","").isEmpty()) {
                    //  if(!surName.getText().toString().replace(" ","").isEmpty()) {
                }
                Intent intent = new Intent(this, LoginActivity.class);
                Call<String> call = httpSevice.register(new RegisterDto(nick.getText().toString(), firstName.getText().toString(), surName.getText().toString(), email.getText().toString(), password.getText().toString()));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            showToast("Registered!");
                            startActivity(intent);
                        }
                        if (response.code() == 400) {
                            showToast("Email already taken!");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });
                saveData(email.getText().toString());

            }
        }
    }


    private void saveData(String spEmail) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY", spEmail);
        editor.apply();
    }

    private void showToast(String text) {
        //TODO trzeba dodac wyswietlanie toasta ze nie moze byc nic puste najlepij
    }
}