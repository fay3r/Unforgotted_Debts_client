package com.example.udclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.udclient.classes.PaymentDto;
import com.example.udclient.classes.PaymentListDto;
import com.example.udclient.classes.TablePaymentAdapter;

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
    private EditText blikPhone,blikValue, blikCode;
    private String id_person,id_meeting;

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
        id_person= intent.getStringExtra("ID_PERSON");
        System.out.println("@$$$$$$$$$$$$$$$$$$$$$$$$$$ "+ id_person + " dane " + id_meeting);
        if(paymentListDto.getPaymentDtoList().size()!=0) {

            recyclerView = findViewById(R.id.recyclerView4);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            adapter = new TablePaymentAdapter(paymentListDto.getPaymentDtoList());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        blikButt = findViewById(R.id.blikButton);
        blikPhone = findViewById(R.id.blikPhoneNumber);
        blikValue = findViewById(R.id.blikValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        blikButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blikPhone.getText().length() == 9) {


                    View mView = inflater.inflate(R.layout.dialog_blik, null);
                    blikCode = mView.findViewById(R.id.blikCode);
                    builder.setView(mView).setTitle("Pan da Pan da")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if(!blikCode.getText().toString().replace(" ","").equals("")){
                                        insertPayment(blikValue.getText().toString(),id_meeting,id_person);
                                    }
                                }
                            });
                    Dialog dialog = builder.create();
                    dialog.show();
                } else {
                    //TODO toast
                }
            }
        });
    }

    public void insertPayment(String value,String id_meeting,String id_person){
        Call<Void> call = httpSevice.insertPayment(new PaymentDto(Float.parseFloat(value),Integer.parseInt(id_meeting),Integer.parseInt(id_person)));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}