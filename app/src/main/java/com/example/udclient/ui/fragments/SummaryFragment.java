package com.example.udclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.udclient.HttpSevice;
import com.example.udclient.R;
import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.classes.PaymentListDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SummaryFragment extends Fragment {

    private String id_person,name,suranme;
    private TextView sumName,sumSurrname,sumValCount,sumTableCount,sumPayCount;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() !=null){
            id_person = getArguments().getString("ID_PERSON");
            name = getArguments().getString("NAME");
            suranme= getArguments().getString("SURNAME");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
;
        View root = inflater.inflate(R.layout.fragment_summary, container, false);

        System.out.println(" to jest moja persona +"+ id_person);
        sumName = root.findViewById(R.id.summName);
        sumPayCount= root.findViewById(R.id.summPayCount);
        sumSurrname= root.findViewById(R.id.sumSurrname);
        sumTableCount= root.findViewById(R.id.sumTabCount);
        sumValCount= root.findViewById(R.id.sumValCount);

        sumName.setText(name);
        sumSurrname.setText(suranme);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        Call<MeetingListDto> call = httpSevice.getPersonsMeetingList(id_person);
        call.enqueue(new Callback<MeetingListDto>() {
            @Override
            public void onResponse(Call<MeetingListDto> call, Response<MeetingListDto> response) {
                MeetingListDto meetingListDto = response.body();
                String count = meetingListDto.getMeetingDtoList().size() + "";
                sumTableCount.setText(count);
            }
            @Override
            public void onFailure(Call<MeetingListDto> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        Call<PaymentListDto> call2 = httpSevice.getPersonPayments(id_person);
        call2.enqueue(new Callback<PaymentListDto>() {
            @Override
            public void onResponse(Call<PaymentListDto> call, Response<PaymentListDto> response) {
                PaymentListDto paymentListDto = response.body();
                String count = paymentListDto.getPaymentDtoList().size() + "";
                sumPayCount.setText(count);
            }
            @Override
            public void onFailure(Call<PaymentListDto> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        Call<Double> call3 = httpSevice.getSumPersonPayments(id_person);
        call3.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Double val = response.body();
                sumValCount.setText(val+" PLN");
            }
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });



        return root;
    }
}