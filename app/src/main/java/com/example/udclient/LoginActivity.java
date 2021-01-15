package com.example.udclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.udclient.classes.LoginDto;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Intent intent;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loadData();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);


    }

    public void logInApp(View view) {
        intent = new Intent(this,Navi_Drawer.class);
        Call<Map<String,String>> call = httpSevice.login(new LoginDto(email.getText().toString(),password.getText().toString()));
        //System.out.println("dziala2");
        call.enqueue(new Callback<Map<String,String>>() {
            @Override
            public void onResponse(Call<Map<String,String>> call, Response<Map<String,String>> response) {

                if( response.code()== 200) {
                    intent.putExtra("LOGIN_NAME", response.body().get("nick"));
                    intent.putExtra("EMAIL", email.getText().toString());
                    intent.putExtra("ID_PERSON",response.body().get("user_id"));
                    saveData(email.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Map<String,String>> call, Throwable t) {
                System.out.println("siema cos nie dziala" + t.getMessage());

            }
        });

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