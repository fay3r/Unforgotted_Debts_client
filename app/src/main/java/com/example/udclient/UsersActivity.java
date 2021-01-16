package com.example.udclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.ui.tables.TablesFragment;
import com.example.udclient.ui.ui.main.SectionsPagerAdapter;
import com.example.udclient.ui.ui.main.UsersFragment;
import com.google.android.material.tabs.TabLayout;

public class UsersActivity extends AppCompatActivity {
    MeetingListDto meetingListDto;
    MeetingDetailsDto meetingDetailsDto;
    TextView title;

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
        meetingListDto = (MeetingListDto) intent.getSerializableExtra("DETAILS");
        title.setText(meetingDetailsDto.getName());

        UsersFragment mFrag = new UsersFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TABLE_DATA", meetingDetailsDto);
        bundle.putSerializable("DETAILS", meetingListDto);
        System.out.println("JESTESMY W USERSACTIVITY " + bundle.getSerializable("TABLE_DATA"));
        mFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, mFrag).commit();
    }
}
