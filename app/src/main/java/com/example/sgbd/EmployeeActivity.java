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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
            maximum = "999999999"; // set a default value if the EditText is empty
        }
        return maximum;
    }
    public static String getArrival(){
        String arrival = arriv.getText().toString(); // get the text entered by the user as a String
        if (arrival.isEmpty()) {
            arrival = "0000-00-00"; // set a default value if the EditText is empty
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

    protected List<Test> doInBackground(Void... voids) {
        List<Test> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        Statement statement2 =null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        String price = null;
        String date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query1;
        String query2;
        String inJoin = "INNER JOIN";

        //the search query for arrival and departure have to be changed
        if(Integer.parseInt(MainActivity.getMin())<Integer.parseInt(MainActivity.getMax())){// checks if the min is less than the max
            price = " prix >= "+MainActivity.getMin() + " AND prix <= " + MainActivity.getMax();
        }
        try {
            if(dateFormat.parse(MainActivity.getArrival()).before(dateFormat.parse(MainActivity.getDeparture()))){//checks if the arrival is before departure
                date = " arrive >= '"+MainActivity.getArrival() +"' AND depart <= '"+ MainActivity.getDeparture()+"'";
            }
        }
        catch(Exception e){
            System.out.print("wrong date format");
        }
        query1 = "CREATE TEMPORARY TABLE temp AS\n"+
                "SELECT * FROM chambre"+"\n"+
                inJoin +" location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre\n" +
                "AND chambre.hadresse = location.hadresse\n"+
                "WHERE " + price + " AND " + date;
        System.out.println("query: "+query1);

        query2 = "SELECT hnom, etoile, nombre_chambre, chaine_nom, temp.numero_chambre, temp.prix, temp.arrive, temp.depart, temp.capacite, temp.superficie\n" +
                "    FROM hotel\n" +
                "\tINNER JOIN temp ON temp.hadresse = hotel.hadresse";



        try {
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            resultSet = statement.executeQuery(query1);
            resultSet2 = statement2.executeQuery(query2);
            while (resultSet2.next()) {
                double prix = resultSet2.getDouble("prix");
                int cap = resultSet2.getInt("capacite");
                double superficie = resultSet2.getDouble("superficie");
                int cat = resultSet2.getInt("category");
                String ch = resultSet2.getString("chaine");
                String hnom = resultSet2.getString("hotel");
                int nChambre = resultSet2.getInt("nombreDeChambres");
                //System.out.println("Prix:"+ prix +" cap: "+ cap + "sup"+ sup);

                dataList.add(new Test(ch, hnom, cat, cap, nChambre, prix, superficie));
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
        EmployeeActivity.mAdapter.notifyDataSetChanged();
    }
}