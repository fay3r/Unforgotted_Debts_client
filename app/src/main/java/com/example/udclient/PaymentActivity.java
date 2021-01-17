package com.example.udclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.udclient.classes.PaymentDto;
import com.example.udclient.classes.PaymentListDto;
import com.example.udclient.classes.TablePaymentAdapter;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TablePaymentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private PaymentListDto paymentListDto;
    private Button blikButt;
    private EditText blikPhone, blikValue, blikCode;
    private String id_person, id_meeting;
    private SwipeRefreshLayout SRL;

    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        Intent intent = getIntent();
        paymentListDto = (PaymentListDto) intent.getSerializableExtra("PAYMENT_DATA");
        id_meeting = intent.getStringExtra("ID_MEETING");
        id_person = intent.getStringExtra("ID_PERSON");

        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TablePaymentAdapter(paymentListDto.getPaymentDtoList());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SRL = findViewById(R.id.swiperefresh3);

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("odswiezam");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Call<PaymentListDto> call = httpSevice.getMeetingsPayments(id_meeting);
                        call.enqueue(new Callback<PaymentListDto>() {
                            @Override
                            public void onResponse(Call<PaymentListDto> call, Response<PaymentListDto> response) {
                                if (response.code() == 200) {
                                    paymentListDto = response.body();
                                    adapter = new TablePaymentAdapter(paymentListDto.getPaymentDtoList());
                                    recyclerView.setAdapter(adapter);
                                    Toast.makeText(PaymentActivity.this, "Refreshed!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PaymentListDto> call, Throwable t) {
                                Toast.makeText(PaymentActivity.this, "Something wentWrong!", Toast.LENGTH_LONG).show();
                            }
                        });
                        SRL.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        blikButt = findViewById(R.id.blikButton);
        blikPhone = findViewById(R.id.blikPhoneNumber);
        blikValue = findViewById(R.id.blikValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        blikButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blikPhone.getText().length() == 9 ) {
                    String check = blikValue.getText().toString();
                    System.out.println("chchchhheckk " + check);
                    if(!check.replace(" ","").isEmpty()){
                        if(Double.valueOf(check) > 0.0) {

                            View mView = inflater.inflate(R.layout.dialog_blik, null);
                            blikCode = mView.findViewById(R.id.blikCode);
                            builder.setView(mView).setTitle("Pan da Pan da")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!blikCode.getText().toString().replace(" ", "").equals("")) {
                                                insertPayment(blikValue.getText().toString(), id_meeting, id_person);
                                            }
                                        }
                                    });
                            Dialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    else{
                        Toast.makeText(PaymentActivity.this, "Zła kwota", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, "Zly numer telefonu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void insertPayment(String value, String id_meeting, String id_person) {
        Call<Void> call = httpSevice.insertPayment(new PaymentDto(Float.parseFloat(value), Integer.parseInt(id_meeting), Integer.parseInt(id_person)));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(PaymentActivity.this,"Platnosc zrealizowana poprawnie!" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Nie udalo sie zrealizowac platnosci", Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());;

            }
        });
    }
}