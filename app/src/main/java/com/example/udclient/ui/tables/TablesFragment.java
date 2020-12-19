package com.example.udclient.ui.tables;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.udclient.Navi_Drawer;
import com.example.udclient.R;
import com.example.udclient.classes.TableAdapter;
import com.example.udclient.classes.TableItem;
import com.example.udclient.ui.TableActivity;

import java.util.ArrayList;


public class TablesFragment extends Fragment {

    private RecyclerView recyclerView;
    private TableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TableItem> list;
    private Button joinTable;
    private EditText exTableCode,exTablePassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (ArrayList) getArguments().getSerializable("LIST");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tables, container, false);

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
                String name = list.get(position).getTableName();
                intent.putExtra("TABLE_NAME", name);
                startActivity(intent);
            }
        });
        joinTable = root.findViewById(R.id.joinExistingTable);
        exTableCode = root.findViewById(R.id.existingTableCode);

        joinTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!exTableCode.getText().toString().replace(" ","").isEmpty()){
                    findTable(exTableCode.getText().toString());}
            }
        });

        return root;
    }

    public void findTable(String code) {

        for (int i = 0; i < list.size(); i++) {
            final TableItem el = list.get(i);
            if (el.getTableCode().equals(code)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View mView = inflater.inflate(R.layout.dialog_tablepassword, null);
                exTablePassword =mView.findViewById(R.id.exTablePassword);

                builder.setView(mView).setTitle("Podaj haslo")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(exTablePassword.getText().toString() +"   powiino byc     " + el.getTablePassword());
                                if(el.getTablePassword().equals(exTablePassword.getText().toString())){
                                    Intent intent = new Intent(getActivity(), TableActivity.class);
                                    String name = el.getTableName();
                                    intent.putExtra("TABLE_NAME", name);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getContext(),"Złe haslo",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();

            }
        }

    }


}