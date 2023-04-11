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

public class ShowClients extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clients);

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