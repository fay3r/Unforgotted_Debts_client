package com.example.udclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.ui.fragments.AddProductFragment;
import com.example.udclient.ui.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class TableActivity extends AppCompatActivity {

    private MeetingDetailsDto meetingDetailsDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        BottomNavigationView navView = findViewById(R.id.nav_view2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        meetingDetailsDto = (MeetingDetailsDto) intent.getSerializableExtra("TABLE_DATA");

        System.out.println(meetingDetailsDto.getName() + " ####################################");

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TABLE_DATA",meetingDetailsDto);
                    selectedFragment = new UsersFragment();
                    selectedFragment.setArguments(bundle);

                }
                if (item.getItemId() == R.id.navigation_dashboard) {
                    selectedFragment = new AddProductFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment2,selectedFragment).commit();
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



