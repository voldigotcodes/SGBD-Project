package com.example.sgbd;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static List<Test> mDataList;
    public static TestAdapter mAdapter;
    public static EditText min, max, arriv, dep;

    RecyclerView mRecyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        min = (EditText) findViewById(R.id.editMinPrice);
        max = (EditText) findViewById(R.id.editMaxPrice);
        arriv = (EditText)findViewById(R.id.editTextArrival);
        dep = (EditText) findViewById(R.id.editTextDep);
        Button hideShow = findViewById(R.id.revealBtn);

        hideShow.setText("SHOW FILTERS");


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
            maximum = "999999999"; // set a default value if the EditText is empty
        }
        return maximum;
    }
    public static String getArrival(){
        String arrival = arriv.getText().toString(); // get the text entered by the user as a String
        if (arrival.isEmpty()) {
            arrival = "0001-01-01"; // set a default value if the EditText is empty
        }
        return arrival;
    }
    public static String getDeparture(){
        String depart = dep.getText().toString(); // get the text entered by the user as a String
        if (depart.isEmpty()) {
            depart = "9999-12-31"; // set a default value if the EditText is empty
        }
        return depart;
    }



    public void test(View view){

        new DatabaseTask(mDataList).execute();
        Toast toast = Toast.makeText(getApplicationContext(),"MIN: "+getMin()+ " MAX: "+getMax(), Toast.LENGTH_SHORT);
        toast.show();

    }

    public void back(View view){
        Intent i = new Intent(getApplicationContext(), LauncherActivity.class);
        startActivity(i);
    }
}

class DatabaseTask extends AsyncTask<Void, Void, List<Test>> {

    private List<Test> mDataList;

    public DatabaseTask(List<Test> dataList) {
        mDataList = dataList;
    }

    @Override
    protected List<Test> doInBackground(Void... voids) {
        List<Test> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String price = null;
        String date = null;
        String capacity = null;
        String sup = null;
        String chaine = null;
        String category = null;
        String nombreChambre = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query;
        String inJoin = "INNER JOIN";

        //the search query for arrival and departure have to be changed
        if (Integer.parseInt(MainActivity.getMin()) < Integer.parseInt(MainActivity.getMax())) {// checks if the min is less than the max
            price = " prix >= " + MainActivity.getMin() + " AND prix <= " + MainActivity.getMax();
        }
        try {
            if(dateFormat.parse(MainActivity.getArrival()).before(dateFormat.parse(MainActivity.getDeparture()))){//checks if the arrival is before departure
                date = " arrive >= '"+MainActivity.getArrival() +"' AND depart <= '"+ MainActivity.getDeparture()+"'";
            }
        }
        catch(Exception e){
            System.out.print("wrong date format");
        }
        query = "SELECT * FROM chambre"+"\n"+
                inJoin +" location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre\n" +
                "AND chambre.hadresse = location.hadresse\n"+
                "WHERE " + price + " AND " + date;
        System.out.println("query: "+query);

        /*SELECT chambre.numero_chambre, chambre.prix, chambre.hadresse, location.arrive, location.depart, sin_c, sin_e
    FROM chambre
	INNER JOIN location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre
	WHERE prix >=500 AND prix <= 1200 AND arrive >='2022-01-31' AND depart <= '2022-12-31'*/

       /* if(MainActivity.getMin().equals("0")&&MainActivity.getMax().equals("0")){ //when there is no min and max entered
            query = "SELECT * FROM testtable1\n"+
                    "WHERE id BETWEEN "+MainActivity.getArrival() + "AND "+MainActivity.getDeparture();
        }
        else if(MainActivity.getArrival().equals("0")&&MainActivity.getDeparture().equals("0")){ //when there is no arrival and departure entered
            query = "SELECT * FROM testtable1\n"+
                    "WHERE salary BETWEEN "+MainActivity.getMin() + "AND "+MainActivity.getMax();
        }
        else{ //uses both type of search method
            query = "SELECT * FROM testtable1\n"+
                    "WHERE id BETWEEN "+MainActivity.getArrival() + "AND "+MainActivity.getDeparture()+"\n"+
                    "AND salary BETWEEN "+MainActivity.getMin() + "AND "+MainActivity.getMax();
        }*/


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                double prix = resultSet.getDouble("prix");
                int cap = resultSet.getInt("capacite");
                double superficie = resultSet.getDouble("superficie");
                //String cat = resultSet.getString("category");
                //String ch = resultSet.getString("chaine");
                //String nChambre = resultSet.getString("nombreDeChambres");
                //System.out.println("Prix:"+ prix +" cap: "+ cap + "sup"+ sup);

                dataList.add(new Test(prix, cap, superficie));
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