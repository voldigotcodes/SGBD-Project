package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShowClients extends AppCompatActivity {

    public static List<Client> mDataList;
    public static ClientAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clients);

        mDataList = new ArrayList<>();

        // create adapter
        mAdapter = new ClientAdapter(mDataList);

        // get reference to RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // set adapter
        mRecyclerView.setAdapter(mAdapter);

        //Fetch all the data the first time
        new ClientDatabaseTask(mDataList, mAdapter).execute();
    }
}

class ClientDatabaseTask extends AsyncTask<Void, Void, List<Client>> {

    private List<Client> mDataList;
    private ClientAdapter adapter;

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