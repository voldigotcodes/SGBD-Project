package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Reservation extends AppCompatActivity {
    public static TextView r_id, dReservation, dReserve, nch, adH;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        r_id = (TextView) findViewById(R.id.rID);
        dReservation = (TextView) findViewById(R.id.dRes);
        dReserve = (TextView) findViewById(R.id.dReserve);
        nch = (TextView) findViewById(R.id.nc);
        adH = (TextView) findViewById(R.id.hotel);

        Intent intent = getIntent();
       // String id = intent.getStringExtra("rId");
        String dR = intent.getStringExtra("Reservation");
        String dr = intent.getStringExtra("Reserve");
        String room = intent.getStringExtra("Chambre");
        String ad = intent.getStringExtra("Adresse");

       // r_id.setText(id);
        dReservation.setText(dR);
        dReserve.setText(dr);
        nch.setText(room);
        adH.setText(ad);
    }

    public void back(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabs);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
