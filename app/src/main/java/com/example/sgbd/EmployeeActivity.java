package com.example.sgbd;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeActivity extends AppCompatActivity {

    private static List<Test> mDataList;
    public static TestAdapter mAdapter;
    public static EditText min, max, arriv, dep;

    RecyclerView mRecyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        min = (EditText) findViewById(R.id.editMinPrice);
        max = (EditText) findViewById(R.id.editMaxPrice);
        arriv = (EditText)findViewById(R.id.editTextArrival);
        dep = (EditText) findViewById(R.id.editTextDep);


        mDataList = new ArrayList<>();

        // create adapter
        mAdapter = new TestAdapter(mDataList);

        // get reference to RecyclerView
        mRecyclerView = findViewById(R.id.hotelList);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // set adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    public void filterReveal(View view){
        Button hideShow = findViewById(R.id.revealBtn);
        View filters = findViewById(R.id.filters);
        ViewGroup.LayoutParams params = filters.getLayoutParams();
        if(hideShow.getText().toString().toUpperCase(Locale.ROOT).startsWith("H")) {
            params.height = findViewById(R.id.searchLinearLayout).getHeight();
            hideShow.setText("SHOW FILTERS");
        } else{
            params.height = WRAP_CONTENT;
            hideShow.setText("HIDE FILTERS");
        }
        filters.setLayoutParams(params);


    }

    public static String getMin() {
        String minimum = min.getText().toString(); // get the text entered by the user as a String
        if (minimum.isEmpty()) {
            minimum = "0"; // set a default value if the EditText is empty
        }
        return minimum;
    }

    public static String getMax(){
        String maximum = max.getText().toString(); // get the text entered by the user as a String
        if (maximum.isEmpty()) {
            maximum = "0"; // set a default value if the EditText is empty
        }
        return maximum;
    }
    public static String getArrival(){
        String arrival = arriv.getText().toString(); // get the text entered by the user as a String
        if (arrival.isEmpty()) {
            arrival = "0"; // set a default value if the EditText is empty
        }
        return arrival;
    }
    public static String getDeparture(){
        String depart = dep.getText().toString(); // get the text entered by the user as a String
        if (depart.isEmpty()) {
            depart = "0"; // set a default value if the EditText is empty
        }
        return depart;
    }



    public void test(View view){

        new EmployeeDatabaseTask(mDataList).execute();
        Toast toast = Toast.makeText(getApplicationContext(),"MIN: "+getMin()+ " MAX: "+getMax(), Toast.LENGTH_SHORT);
        toast.show();

    }

    public void onClickAddHotel(View view){
        Intent i = new Intent(getApplicationContext(), AddHotelActivity.class);
        startActivity(i);
    }
}

class EmployeeDatabaseTask extends AsyncTask<Void, Void, List<Test>> {

    private List<Test> mDataList;
    private String table = "test";

    public EmployeeDatabaseTask(List<Test> dataList) {
        mDataList = dataList;
    }

    @Override
    protected List<Test> doInBackground(Void... voids) {
        List<Test> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query;

        //the search query for arrival and departure have to be changed
        if(MainActivity.getMin().equals("0")&&MainActivity.getMax().equals("0")){ //when there is no min and max entered
            query = "SELECT * FROM "+table+"\n"+
                    "WHERE id BETWEEN "+MainActivity.getArrival() + "AND "+MainActivity.getDeparture();
        }
        else if(MainActivity.getArrival().equals("0")&&MainActivity.getDeparture().equals("0")){ //when there is no arrival and departure entered
            if(Integer.parseInt(MainActivity.getMax())>=Integer.parseInt(MainActivity.getMin())){
                query = "SELECT * FROM "+table+"\n"+
                        "WHERE salary > "+MainActivity.getMin() + "AND salary < " + MainActivity.getMax();
            }else{
                query = "SELECT * FROM "+table+"\n"+
                        "WHERE salary > "+MainActivity.getMin();
            }
        }
        else{ //uses both type of search method
            query = "SELECT * FROM "+table+"\n"+
                    "WHERE id BETWEEN "+MainActivity.getArrival() + "AND "+MainActivity.getDeparture()+"\n"+
                    "AND salary BETWEEN "+MainActivity.getMin() + "AND "+MainActivity.getMax();
        }


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int salary = resultSet.getInt("salary");
                dataList.add(new Test(id, name, age, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Override
    protected void onPostExecute(List<Test> dataList) {
        super.onPostExecute(dataList);
        mDataList.clear();
        mDataList.addAll(dataList);
        MainActivity.mAdapter.notifyDataSetChanged();
    }
}