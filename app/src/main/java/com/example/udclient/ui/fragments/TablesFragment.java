package com.example.udclient.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.udclient.TableActivity;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.classes.TableAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TablesFragment extends Fragment {

    private RecyclerView recyclerView;
    private TableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingListDto list;
    private Button joinTable;
    private EditText exTableCode, exTablePassword;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";
    private Intent intent;
    private String nick, id_person;
    private SwipeRefreshLayout mSRL;
    //private FragmentManager fragmentManager = getActivity().getFragmentManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (MeetingListDto) getArguments().getSerializable("DETAILS");
            nick = getArguments().getString("USER_NICK");
            id_person =getArguments().getString("USER_ID");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ w table fragmencie "  +  nick);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tables, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TableAdapter(list.getMeetingDtoList());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mSRL = root.findViewById(R.id.swiperefresh);
        mSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("odswiezam");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Fragment mFrag = new TablesFragment();
                        Bundle bundle = new Bundle();
                        Call<MeetingListDto> call = httpSevice.getPersonsMeetingList(id_person);
                        call.enqueue(new Callback<MeetingListDto>() {
                            @Override
                            public void onResponse(Call<MeetingListDto> call, Response<MeetingListDto> response) {
                                list = response.body();
                                adapter = new TableAdapter(list.getMeetingDtoList());
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemListener(new TableAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        String code = list.getMeetingDtoList().get(position).getCode();
                                        System.out.println("#########################################"+code);
                                        goToTable(code);
                                    }
                                });
                                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_LONG).show();

                            }
                            @Override
                            public void onFailure(Call<MeetingListDto> call, Throwable t) {
                                System.out.println(t.getMessage());
                                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        });
                        mSRL.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        adapter.setOnItemListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = list.getMeetingDtoList().get(position).getCode();
                System.out.println("#########################################"+code);
                goToTable(code);

            }
        });
        joinTable = root.findViewById(R.id.joinExistingTable);
        exTableCode = root.findViewById(R.id.existingTableCode);

        joinTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!exTableCode.getText().toString().replace(" ", "").isEmpty()) {
                    findTable(exTableCode.getText().toString());
                }
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        return root;
    }

    public void findTable(final String code) {
        Intent intent = new Intent(getActivity(), TableActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_tablepassword, null);
        exTablePassword = mView.findViewById(R.id.exTablePassword);

        builder.setView(mView).setTitle("Password")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Call<Void> call = httpSevice.joinMeeting(code, exTablePassword.getText().toString());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code()==200){
                                    System.out.println("@@@@@@@@@@@@@małpa");
                                    goToTable(code);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.err.println(t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();

    }

    public void goToTable(String code){
        intent = new Intent(getContext(), TableActivity.class);

        Call<MeetingDetailsDto> call = httpSevice.getMeetingDetails(code);

        call.enqueue(new Callback<MeetingDetailsDto>() {
            @Override
            public void onResponse(Call<MeetingDetailsDto> call, Response<MeetingDetailsDto> response) {
                MeetingDetailsDto meetingDetailsDto = response.body();
                intent.putExtra("TABLE_DATA",meetingDetailsDto);
                intent.putExtra("USER_NICK",nick);

                startActivity(intent);
            }

            @Override
            public void onFailure(Call<MeetingDetailsDto> call, Throwable t) {
                System.err.println(t.getMessage());

            }
        });
    }
}




