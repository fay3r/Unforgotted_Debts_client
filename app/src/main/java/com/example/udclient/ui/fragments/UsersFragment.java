package com.example.udclient.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.PersonMeetingDto;
import com.example.udclient.classes.TableAdapter;
import com.example.udclient.classes.TableUserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    private MeetingDetailsDto meetingDetailsDto;
    private RecyclerView recyclerView;
    private TableUserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PersonMeetingDto> users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            System.out.println("dzialam wczesnije");
            meetingDetailsDto = (MeetingDetailsDto) getArguments().getSerializable("TABLE_DATA");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_users, container, false);
        users=meetingDetailsDto.getPersonMeetingList();
        recyclerView = root.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TableUserAdapter(users);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new TableUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUserDetails(users.get(position));
            }
        });

        return root;
    }

    public void showUserDetails(PersonMeetingDto personMeetingDto){
        TextView nick,name,surname,email;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_userdetails, null);
        nick = mView.findViewById(R.id.userNickDet);
        name = mView.findViewById(R.id.userNameDet);
        surname = mView.findViewById(R.id.userSurDet);
        email = mView.findViewById(R.id.userMailDet);

        nick.setText(personMeetingDto.getNick());
        name.setText(personMeetingDto.getName());
        surname.setText(personMeetingDto.getSurname());
        email.setText(personMeetingDto.getEmail());

        builder.setView(mView).setTitle("Uzytkownik")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }
}