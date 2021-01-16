package com.example.udclient.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.udclient.HttpSevice;
import com.example.udclient.PaymentActivity;
import com.example.udclient.R;
import com.example.udclient.classes.PaymentListDto;
import com.example.udclient.classes.ProductDto;
import com.example.udclient.classes.ProductListDto;
import com.example.udclient.classes.TableProductAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductFragment extends Fragment {

    private ProductListDto productListDto;
    private List<ProductDto> products;
    private  RecyclerView recyclerView;
    private TableProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int members,id_person,id_meeting;
    private String permissions;

    private TextView tPaid, tExpenses,tpartCount,costPerPer;
    private Button addProduct,addPayment;

    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            productListDto = (ProductListDto) getArguments().getSerializable("PRODUCT_DATA");
            members= getArguments().getInt("NUMMEM");
            permissions = getArguments().getString("USER_PERMISSIONS");
            id_person = getArguments().getInt("ID_PERSON");
            id_meeting = getArguments().getInt("ID_MEETING");
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
                showProductDetails(products.get(position));
            }
        });

        tExpenses = root.findViewById(R.id.totalExpenses);
        tPaid = root.findViewById(R.id.paid);
        tpartCount = root.findViewById(R.id.partCount);
        addProduct = root.findViewById(R.id.addProduct);
        costPerPer = root.findViewById(R.id.restPerMembers);
        addPayment = root.findViewById(R.id.addPayment);

        double sum=0;
        for (ProductDto productDto :
                products) {
            sum+=productDto.getPrice();
        }
        tExpenses.setText(sum+"");
        tpartCount.setText(members+"");
        costPerPer.setText(Double.toString(sum/members));


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newProdName, newProdPrice;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View mView = inflater.inflate(R.layout.dialog_addproduct, null);
                newProdName = mView.findViewById(R.id.addProductName);
                newProdPrice = mView.findViewById(R.id.addProductPrice);
                builder.setView(mView).setTitle("Dodaj cosik")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(" id nervy "+id_person);
                                 addProductFun(newProdName.getText().toString() ,  newProdPrice.getText().toString());
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inetn = new Intent(getContext(), PaymentActivity.class);
                Call<PaymentListDto> call = httpSevice.getMeetingsPayments(Integer.toString(id_meeting));
                call.enqueue(new Callback<PaymentListDto>() {
                    @Override
                    public void onResponse(Call<PaymentListDto> call, Response<PaymentListDto> response) {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + response.code());
                        if(response.code()==200) {
                            PaymentListDto paymentListDto = response.body();
                            System.out.println("w paymentach" + paymentListDto.getPaymentDtoList().get(0).getValue());
                            inetn.putExtra("PAYMENT_DATA", paymentListDto);
                            startActivity(inetn);
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentListDto> call, Throwable t) {

                    }
                });

            }
        });



        return root;
    }

    public void addProductFun(String name ,String price){

        Call<Void> call = httpSevice.addProduct(name,price,Integer.toString(id_person),Integer.toString(id_meeting));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if( response.code() == 200) {
                    Toast.makeText(getContext(), ("Dodano " + name), Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getContext(), "zyebao", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    public void showProductDetails(ProductDto productDto){
        TextView proPrice,proNick,proDate,proTime;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_prductdetails, null);
        proPrice = mView.findViewById(R.id.prodPrice);
        proNick = mView.findViewById(R.id.prodNick);
        proDate = mView.findViewById(R.id.prodDate);
        proTime = mView.findViewById(R.id.prodTime);


        proPrice.setText(productDto.getPrice().toString());
        proNick.setText(productDto.getNick());
        proDate.setText(productDto.getDate());
        proTime.setText(productDto.getTime());


        if(permissions.equals("owner")) {
            builder.setView(mView).setTitle(productDto.getName())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).setNegativeButton("Usun",
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<Void> call = httpSevice.deleteProduct(productDto.getId_product().toString());

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        System.out.println("wzialem i usunalem");
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });
                            }
                        });
        } else {
            builder.setView(mView).setTitle("Uzytkownik")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
        }
        Dialog dialog = builder.create();
        dialog.show();
    }
}