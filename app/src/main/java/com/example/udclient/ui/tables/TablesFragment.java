package com.example.udclient.ui.tables;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;
import com.example.udclient.classes.TableAdapter;
import com.example.udclient.classes.TableItem;
import com.example.udclient.ui.TableActivity;

import java.util.ArrayList;


public class TablesFragment extends Fragment {

    private TablesViewModel galleryViewModel;
    private Button tableButton;
    private RecyclerView recyclerView;
    private TableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(TablesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listoftables, container, false);

//        tableButton = root.findViewById(R.id.clicker);
//        tableButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//            }
//        });

        final ArrayList<TableItem> list = new ArrayList<>();
        list.add(new TableItem("pierwsza","gracjan"));
        list.add(new TableItem("druga","horwacy"));
        list.add(new TableItem("trzecia","kazek"));

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TableAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TableActivity.class);
                System.out.println(list.get(position).getTableName());
                intent.putExtra("TABLE_NAME",list.get(position).getTableName());
                startActivity(intent);
            }
        });

        return root;
    }


}