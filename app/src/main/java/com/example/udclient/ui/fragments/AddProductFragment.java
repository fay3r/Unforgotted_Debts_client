package com.example.udclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.ProductDto;
import com.example.udclient.classes.ProductListDto;
import com.example.udclient.classes.TableProductAdapter;
import com.example.udclient.classes.TableUserAdapter;

import java.util.List;

public class AddProductFragment extends Fragment {

    private ProductListDto productListDto;
    private List<ProductDto> products;
    private  RecyclerView recyclerView;
    private TableProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int members;

    private TextView tPaid, tExpenses,tpartCount;
    private Button addP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            productListDto = (ProductListDto) getArguments().getSerializable("PRODUCT_DATA");
            members= getArguments().getInt("NUMMEM");
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        products=productListDto.getProductDtoList();
        recyclerView = root.findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TableProductAdapter(products);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new TableProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        tExpenses = root.findViewById(R.id.totalExpenses);
        tPaid = root.findViewById(R.id.paid);
        tpartCount = root.findViewById(R.id.partCount);
        addP = root.findViewById(R.id.addProduct);

        double sum=0;
        for (ProductDto productDto :
                products) {
            sum+=productDto.getPrice();
        }
        tExpenses.setText(sum+"");
        tpartCount.setText(members+"");
        


        return root;
    }
}