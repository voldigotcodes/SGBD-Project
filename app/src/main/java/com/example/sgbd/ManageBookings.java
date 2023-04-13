package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManageBookings extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabs);

        mViewPager.setAdapter(new ReservationPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}

class ManageReservationDatabaseTask extends AsyncTask<Void, Void, List<ReservationModel>> {

    public static List<ReservationModel> mDataList;
    public static ReservationAdapter adapter;

    public ManageReservationDatabaseTask(List<ReservationModel> dataList, ReservationAdapter adapter) {
        mDataList = dataList;
        this.adapter = adapter;
    }

    @Override
    protected List<ReservationModel> doInBackground(Void... voids) {
        List<ReservationModel> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM reservation";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("r_id");
                String date_reservation = resultSet.getString("date_reservation");
                String date_reserve = resultSet.getString("date_reserve");
                int numero_chambre = resultSet.getInt("numero_chambre");
                String sin_c = resultSet.getString("sin_c");
                String l_id = resultSet.getString("l_id");
                String hAdresse = resultSet.getString("hadresse");

                dataList.add(new ReservationModel(id, date_reservation, date_reserve, numero_chambre, sin_c, l_id, hAdresse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(List<ReservationModel> dataList) {
        super.onPostExecute(dataList);
        mDataList.clear();
        mDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }
}

class ManageLocationDatabaseTask extends AsyncTask<Void, Void, List<LocationModel>> {

    public static List<LocationModel> mDataList;
    public static LocationAdapter adapter;

    public ManageLocationDatabaseTask(List<LocationModel> dataList, LocationAdapter adapter) {
        mDataList = dataList;
        this.adapter = adapter;
    }

    @Override
    protected List<LocationModel> doInBackground(Void... voids) {
        List<LocationModel> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM location";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("l_id");
                String arrive = resultSet.getString("arrive");
                String depart = resultSet.getString("depart");
                int numero_chambre = resultSet.getInt("numero_chambre");
                int sin_c = resultSet.getInt("sin_c");
                int sin_e = resultSet.getInt("sin_e");
                String hAdresse = resultSet.getString("hadresse");

                dataList.add(new LocationModel(id, arrive, depart, numero_chambre, sin_c, sin_e, hAdresse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(List<LocationModel> dataList) {
        super.onPostExecute(dataList);
        mDataList.clear();
        mDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }
}