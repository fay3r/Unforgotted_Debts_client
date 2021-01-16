package com.example.udclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;

public class UsersFragment extends Fragment {

    MeetingDetailsDto meetingDetailsDto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            meetingDetailsDto = (MeetingDetailsDto) getArguments().getSerializable("TABLE_DATA");
            System.out.println("##############$#$#$#$# w userach"+meetingDetailsDto.getPersonMeetingList().toString());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_users, container, false);


       // System.out.println("##############$#$#$#$# w userach"+meetingDetailsDto.getPersonMeetingList().toString());
        return root;
    }
}