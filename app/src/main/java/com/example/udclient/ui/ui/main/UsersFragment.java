package com.example.udclient.ui.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.classes.TableAdapter;
import com.example.udclient.classes.TableUserAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class UsersFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private TableUserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingDetailsDto meetingDetailsDto;
    private MeetingListDto meetingListDto = new MeetingListDto();

    public static UsersFragment newInstance(int index) {
        UsersFragment fragment = new UsersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // int index = 1;
        if (getArguments() != null) {
            //index = getArguments().getInt(ARG_SECTION_NUMBER);
            //meetingDetailsDto = (MeetingDetailsDto) getArguments().getSerializable("TABLE_DATA");
            meetingDetailsDto = (MeetingDetailsDto) getArguments().getSerializable("DETAILS");
            //System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+meetingDetailsDto.getPersonMeetingList().get(0));
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_users, container, false);
        adapter = new TableUserAdapter(meetingListDto.getMeetingDtoList());

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());

        //System.out.println(meetingListDto.getMeetingDtoList().get(0).getName());
       // System.out.println("__________________" + adapter.getItemCount() + adapter.getItemId(0) + adapter.getItemViewType(0));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /*adapter.setOnItemListener(new TableUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });*/

        return root;
    }
}