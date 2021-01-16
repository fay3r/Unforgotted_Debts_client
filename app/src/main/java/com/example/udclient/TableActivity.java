package com.example.udclient;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.udclient.ui.dashboard.DashboardFragment;
import com.example.udclient.ui.home.HomeFragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        BottomNavigationView navView = findViewById(R.id.nav_view2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navView.setSelectedItemId(R.id.home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new HomeFragment2();

                }
                if (item.getItemId() == R.id.navigation_dashboard) {
                    selectedFragment = new DashboardFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2,selectedFragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2,
                    new HomeFragment2()).commit();
        }
    }

}



