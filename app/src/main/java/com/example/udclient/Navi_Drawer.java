package com.example.udclient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.udclient.classes.TableItem;
import com.example.udclient.ui.home.HomeFragment;
import com.example.udclient.ui.summary.SummaryFragment;
import com.example.udclient.ui.tables.TablesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Navi_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private String nick,email;
    private TextView drawerNickField, drawerEmailField;
    private ArrayList<TableItem> list;
    private EditText tableName, tablePassword;
    private TablesFragment tablesFragment;
    private SummaryFragment summaryFragment;
    private HomeFragment homeFragment;
    private  DrawerLayout drawer;

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

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);

        View headerView = navigationView.getHeaderView(0);

        Intent intent = getIntent();
        nick=intent.getStringExtra("LOGIN_NAME");
        email=intent.getStringExtra("EMAIL");

        drawerEmailField = headerView.findViewById(R.id.drawerEmail);
        drawerEmailField.setText(email);

       // drawerNickField = headerView.findViewById(R.id.drawerNick);
        //drawerNickField.setText("Unforgotten debts");

        list = new ArrayList<>();
        list.add(new TableItem("Imprezka","gracjan","12345","123"));
        list.add(new TableItem("Osiemnastka","horwacy","54321","321"));
        list.add(new TableItem("Kawalerski","kazek","98765","lolek"));
        list.add(new TableItem("Kawalerski2","misiek","9452","lolek"));
        list.add(new TableItem("Kawalerski3","dzbanek","91254","lolek"));
        list.add(new TableItem("Kawalerski4","krycha","96874","lolek"));
        list.add(new TableItem("Kawalerski5","gruby","95632","lolek"));
        list.add(new TableItem("Kawalerski6","domel","92456","lolek"));
        list.add(new TableItem("Kawalerski7","jjes","94786","lolek"));

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

        builder.setView(mView).setTitle("Nowy stół")
                .setPositiveButton("Stwórz", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println(tableName.getText().toString() + " adsada"  +tablePassword.getText().toString());
                        list.add(new TableItem(tableName.getText().toString(),nick));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_tables){
            TablesFragment mFrag = new TablesFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST",list);   //parameters are (key, value).
            mFrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,mFrag).commit();
        }
        if(item.getItemId() == R.id.nav_summary){getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new SummaryFragment()).commit();}
        if(item.getItemId() == R.id.nav_home){getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();}

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}