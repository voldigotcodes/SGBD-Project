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

class ClientDatabaseTask extends AsyncTask<Void, Void, List<Client>> {

    public static List<Client> mDataList;
    public static ClientAdapter adapter;

    public ClientDatabaseTask(List<Client> dataList, ClientAdapter adapter) {
        mDataList = dataList;
        this.adapter = adapter;

    }

    @Override
    protected List<Client> doInBackground(Void... voids) {
        List<Client> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM client";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nom = resultSet.getString("cnom");
                String sin = resultSet.getString("sin_c");
                String adresse = resultSet.getString("client_adresse");
                String enregistrement = resultSet.getString("date_enregistrement");
                String passeword = resultSet.getString("cpassword");
                String email = resultSet.getString("client_email");

                dataList.add(new Client(nom, sin, adresse, enregistrement, passeword, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(List<Client> dataList) {
        super.onPostExecute(dataList);
        mDataList.clear();
        mDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }
}

class ManageEmployeeDatabaseTask extends AsyncTask<Void, Void, List<Employee>> {

    private List<Employee> eDataList;
    private EmployeeAdapter adapter;

    public ManageEmployeeDatabaseTask(List<Employee> dataList, EmployeeAdapter adapter) {
        eDataList = dataList;
        this.adapter = adapter;
    }

    @Override
    protected List<Employee> doInBackground(Void... voids) {
        List<Employee> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM employe";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nom = resultSet.getString("enom");
                String sin = resultSet.getString("sin_e");
                String role = resultSet.getString("role");
                String adresse = resultSet.getString("eadresse");
                String hAdresse = resultSet.getString("hadresse");
                String passeword = resultSet.getString("epassword");
                String email = resultSet.getString("employe_email");

                dataList.add(new Employee(nom, sin, role, adresse, hAdresse, passeword, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(List<Employee> dataList) {
        super.onPostExecute(dataList);
        eDataList.clear();
        eDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }
}