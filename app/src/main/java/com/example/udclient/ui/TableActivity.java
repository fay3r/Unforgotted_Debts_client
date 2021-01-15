package com.example.udclient.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.udclient.R;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.PersonMeetingDto;
import com.example.udclient.ui.tables.TablesFragment;
import com.example.udclient.ui.ui.main.AddFragment;
import com.example.udclient.ui.ui.main.UsersFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.udclient.ui.ui.main.SectionsPagerAdapter;

import java.util.List;

public class TableActivity extends AppCompatActivity {

    MeetingDetailsDto meetingDetailsDto;
    TextView title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        title = findViewById(R.id.tabTitle);
        Intent intent = getIntent();
        meetingDetailsDto = (MeetingDetailsDto) intent.getSerializableExtra("TABLE_DATA");
        title.setText(meetingDetailsDto.getName());

        TablesFragment mFrag = new TablesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TABLE_LIST",meetingDetailsDto);
        System.out.println("bundndndndnnle ##############" + bundle.getSerializable("TABLE_LIST"));
        mFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager,mFrag).commit();


    }
}