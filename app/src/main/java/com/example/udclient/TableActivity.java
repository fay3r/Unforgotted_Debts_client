package com.example.udclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.PersonMeetingDto;
import com.example.udclient.classes.ProductDto;
import com.example.udclient.classes.ProductListDto;
import com.example.udclient.ui.fragments.AddProductFragment;
import com.example.udclient.ui.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableActivity extends AppCompatActivity {

    private MeetingDetailsDto meetingDetailsDto;
    private ProductListDto productListDto;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.121:8080/";
    private String nick;
    private int id_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        BottomNavigationView navView = findViewById(R.id.nav_view2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        meetingDetailsDto = (MeetingDetailsDto) intent.getSerializableExtra("TABLE_DATA");
        nick = intent.getStringExtra("USER_NICK");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TABLE_DATA",meetingDetailsDto);
                    bundle.putInt("ID_MEETING",meetingDetailsDto.getId_meeting());
                    selectedFragment = new UsersFragment();
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2,selectedFragment).commit();

                }
                if (item.getItemId() == R.id.navigation_dashboard) {

                    Call<ProductListDto> call = httpSevice.getMeetingsProducts(meetingDetailsDto.getId_meeting().toString());

                    call.enqueue(new Callback<ProductListDto>() {
                        @Override
                        public void onResponse(Call<ProductListDto> call, Response<ProductListDto> response) {
                            if(response.code()==200) {
                                System.out.println(url);
                                Fragment frag = new AddProductFragment();
                                productListDto = response.body();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("PRODUCT_DATA", productListDto);
                                bundle.putInt("NUMMEM",meetingDetailsDto.getPersonMeetingList().size());

                                for(PersonMeetingDto personMeetingDto : meetingDetailsDto.getPersonMeetingList()){
                                    System.out.println(personMeetingDto.getNick() + " rowna sie " + nick);
                                    if(personMeetingDto.getNick().equals(nick)){
                                        nick = personMeetingDto.getUser_type();
                                        id_person = personMeetingDto.getId_person();
                                        break;
                                    }
                                }
                                System.out.println("uprawnienia  " + nick );
                                bundle.putString("USER_PERMISSIONS", nick);
                                bundle.putInt("ID_PERSON",id_person);
                                bundle.putInt("ID_MEETING",meetingDetailsDto.getId_meeting());
                                frag.setArguments(bundle);

                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2, frag).commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductListDto> call, Throwable t) {
                            System.out.println(t.getMessage());

                        }
                    });


                }

                return true;
            }
        });
        navView.setSelectedItemId(R.id.home);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("TABLE_DATA",meetingDetailsDto);
            UsersFragment frag = new UsersFragment();
            frag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2,
                    frag).commit();
        }
    }

}



