package com.example.udclient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.udclient.HttpSevice;
import com.example.udclient.R;
import com.example.udclient.classes.MeetingListDto;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private TextView nameTextView, surnameTextView;
    //private String name, surname;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //System.out.println("==================================================================jjjjjdddd");
            //name = getArguments().getString("NAMEHOME");
            //surname = getArguments().getString("SURNAMEHOME");
        }
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //System.out.println(" To w onCreate View Imie: " + name + " Nazwisko: " + surname);

        /*nameTextView = root.findViewById(R.id.nameOnHome);
        surnameTextView = root.findViewById(R.id.surnameOnHome);
        nameTextView.setText(name);
        surnameTextView.setText(surname);*/
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}