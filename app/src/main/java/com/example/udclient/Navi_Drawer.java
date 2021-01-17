package com.example.udclient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.ui.fragments.HomeFragment;
import com.example.udclient.ui.fragments.SummaryFragment;
import com.example.udclient.ui.fragments.TablesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Navi_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private String nick,email,idPerson, name, surname;
    private TextView drawerNickField, drawerEmailField;
    private EditText tableName, tablePassword;
    private TablesFragment tablesFragment;
    private SummaryFragment summaryFragment;
    private HomeFragment homeFragment;
    private  DrawerLayout drawer;
    private HttpSevice httpSevice;
    private static String url = "http://192.168.0.104:8080/";
    private Intent intent;
    private Bundle bundle;
    private TablesFragment mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi__drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTable();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        summaryFragment= new SummaryFragment();
        homeFragment = new HomeFragment();
        tablesFragment = new TablesFragment();

        Intent intent = getIntent();
        nick=intent.getStringExtra("LOGIN_NAME");
        email=intent.getStringExtra("EMAIL");
        idPerson=intent.getStringExtra("ID_PERSON");
        name = intent.getStringExtra("NAME");
        surname = intent.getStringExtra("SURNAME");
        System.out.println("dane uzytkownika "+  nick + email + idPerson + name + surname);;

        Bundle bundle1 = new Bundle();
        bundle1.putString("NAMEHOME", name);
        bundle1.putString("SURNAMEHOME", surname);
        HomeFragment fragmentHome = new HomeFragment();
        fragmentHome.setArguments(bundle1);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragmentHome).commit();
        navigationView.setCheckedItem(R.id.nav_home);

        View headerView = navigationView.getHeaderView(0);


        drawerEmailField = headerView.findViewById(R.id.drawerEmail);
        drawerEmailField.setText(nick);

       // drawerNickField = headerView.findViewById(R.id.drawerNick);
        //drawerNickField.setText("Unforgotten debts");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        httpSevice = retrofit.create(HttpSevice.class);

    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Unforgotten Debts");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navi_Drawer.super.onBackPressed();
                    }
                });
        builder.setNegativeButton("No stay logged", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void createTable(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = Navi_Drawer.this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_newtable,null);
        tableName = mView.findViewById(R.id.newTableName);
        tablePassword = mView.findViewById(R.id.newTablePassword);
        intent = new Intent(this, TableActivity.class);

        builder.setView(mView).setTitle("New Table")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println(tableName.getText().toString() + " siema cos nie dziala "  +tablePassword.getText().toString());
                        Call<Void> call = httpSevice.createMeeting(tableName.getText().toString(),tablePassword.getText().toString());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code()==200){
                                    intent.putExtra("TABLE_NAME", tableName.getText().toString());
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.err.println(t.getMessage());
                                Toast.makeText(Navi_Drawer.this, "Server is offline", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_tables){
            mFrag = new TablesFragment();
            bundle = new Bundle();
            Call<MeetingListDto> call = httpSevice.getPersonsMeetingList(idPerson);
            call.enqueue(new Callback<MeetingListDto>() {
                @Override
                public void onResponse(Call<MeetingListDto> call, Response<MeetingListDto> response) {
                    System.err.println(response.body());
                    System.err.println(response.code());
                    MeetingListDto meetingListDto = response.body();
                    System.out.println(meetingListDto.getMeetingDtoList().get(0));
                    bundle.putSerializable("DETAILS",meetingListDto);
                    bundle.putString("USER_NICK",nick);
                    bundle.putString("USER_ID",idPerson);

                    mFrag.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, mFrag).commit();
                }
                @Override
                public void onFailure(Call<MeetingListDto> call, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });
        }
        if(item.getItemId() == R.id.nav_summary){
            Bundle bundle = new Bundle();
            bundle.putString("ID_PERSON", idPerson);
            bundle.putString("NAME", name);
            bundle.putString("SURNAME", surname);
            SummaryFragment summaryFragment =new SummaryFragment();
            summaryFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,summaryFragment).commit();}
        if(item.getItemId() == R.id.nav_home){
            Bundle bundle1 = new Bundle();
            bundle1.putString("NAME", name);
            bundle1.putString("SURNAME", surname);
            HomeFragment fragmentHome = new HomeFragment();
            fragmentHome.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragmentHome).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}