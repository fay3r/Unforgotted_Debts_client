package com.example.udclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.udclient.classes.PaymentListDto;
import com.example.udclient.classes.TablePaymentAdapter;

public class PaymentActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TablePaymentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private PaymentListDto paymentListDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        System.out.println("jestem w apymentach");

        Intent intent = getIntent();
        paymentListDto = (PaymentListDto) intent.getSerializableExtra("PAYMENT_DATA");
        System.out.println("@$$$$$$$$$$$$$$$$$$$$$$$$$$ "+ paymentListDto);
//        if(paymentListDto.getPaymentDtoList().size()!=0) {
//
//            recyclerView = findViewById(R.id.recyclerView4);
//            recyclerView.setHasFixedSize(true);
//            layoutManager = new LinearLayoutManager(this);
//            adapter = new TablePaymentAdapter(paymentListDto.getPaymentDtoList());
//
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);
       // }
    }
}