package com.example.udclient.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.udclient.HttpSevice;
import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.PersonMeetingDto;
import com.example.udclient.classes.TableUserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersFragment extends Fragment {

    private MeetingDetailsDto meetingDetailsDto;
    private RecyclerView recyclerView;
    private TableUserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PersonMeetingDto> users;
    private EditText addPerNick;
    private Button addPerson;
    private Integer id_meeting;

    private SwipeRefreshLayout SRL;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.121:8080/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            System.out.println("dzialam wczesnije");
            meetingDetailsDto = (MeetingDetailsDto) getArguments().getSerializable("TABLE_DATA");
            id_meeting = getArguments().getInt("ID_MEETING");
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

        SRL = root.findViewById(R.id.swiperefresh4);

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("odswiezam");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Call<MeetingDetailsDto> call = httpSevice.getMeetingDetails(meetingDetailsDto.getCode());
                        call.enqueue(new Callback<MeetingDetailsDto>() {
                            @Override
                            public void onResponse(Call<MeetingDetailsDto> call, Response<MeetingDetailsDto> response) {
                                meetingDetailsDto=response.body();
                                users=meetingDetailsDto.getPersonMeetingList();
                                adapter = new TableUserAdapter(users);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemListener(new TableUserAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        showUserDetails(users.get(position));
                                    }
                                });
                                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<MeetingDetailsDto> call, Throwable t) {
                                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        });
                        SRL.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        adapter.setOnItemListener(new TableUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUserDetails(users.get(position));
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        addPerNick = root.findViewById(R.id.userToAdd);
        addPerson = root.findViewById(R.id.userAddButton);

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = httpSevice.addPerson(meetingDetailsDto.getId_meeting().toString(),addPerNick.getText().toString()) ;

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200){
                            Toast.makeText(getContext(), ("Dodano " + addPerNick.getText().toString()), Toast.LENGTH_LONG).show();
                        }
                        if(response.code()==400){
                            Toast.makeText(getContext(), (addPerNick.getText().toString() + " nie istnieje"), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        return root;
    }

    public void showUserDetails(PersonMeetingDto personMeetingDto){
        TextView nick,name,surname,email;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_userdetails, null);
        nick = mView.findViewById(R.id.prodNick);
        name = mView.findViewById(R.id.prodName);
        surname = mView.findViewById(R.id.prodPrice);
        email = mView.findViewById(R.id.prodDate);

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