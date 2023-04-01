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

    public static List<Test> mDataList;
    public static TestAdapter mAdapter;
    public static EditText min, max, arriv, dep, capacity, area, cHot, cat, numCh;

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
        capacity = (EditText) findViewById(R.id.capacityEditText);
        area = (EditText) findViewById(R.id.sizeEditText);
        cHot = (EditText) findViewById(R.id.chaineEditText);
        cat = (EditText) findViewById(R.id.categoryEditText);
        numCh = (EditText) findViewById(R.id.capacityEditText);
//have to set up the rest of the editText variables
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
    public static String getCap(){
        String c = capacity.getText().toString(); // get the text entered by the user as a String
        if (c.isEmpty()) {
            c = "0"; // set a default value if the EditText is empty
        }
        return c;
    }

    public static String getArea(){
        String a = area.getText().toString(); // get the text entered by the user as a String
        if (a.isEmpty()) {
            a = "0"; // set a default value if the EditText is empty
        }
        return a;
    }
    public static String getChot(){
        String cH = cHot.getText().toString(); // get the text entered by the user as a String
        if (cH.isEmpty()) {
            cH = ""; // set a default value if the EditText is empty
        }
        return cH;
    }
    public static String getCat(){
        String ca = cat.getText().toString(); // get the text entered by the user as a String
        if (ca.isEmpty()) {
            ca = "0"; // set a default value if the EditText is empty
        }
        return ca;
    }
    public static String getnCh(){
        String nCh = numCh.getText().toString(); // get the text entered by the user as a String
        if (nCh.isEmpty()) {
            nCh = "0"; // set a default value if the EditText is empty
        }
        return nCh;
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
        ResultSet resultSet = null;
        String price = " prix >= " + MainActivity.getMin() + " AND prix <= " + MainActivity.getMax();
        String date = null;
        String capacite=  capacite = " AND chambre.capacite >= "+ MainActivity.getCap();
        String superficie =" AND chambre.superficie >= "+ MainActivity.getArea();
        String chaine = "";
        String numChambre = null;
        String categorie = " etoile >= '"+ MainActivity.getCat()+"'";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query1;
        String query2;

        if(!MainActivity.getChot().equals("")){//in query2
            chaine = " AND chaine_nom = '"+MainActivity.getChot()+"'";
        }

        if(Integer.parseInt(MainActivity.getCap())<0){
            capacite = " AND chambre.capacite >= 0";
        }


        if(Double.parseDouble(MainActivity.getArea())<0){
            superficie = " AND chambre.superficie >= 0";
        }

        if(Integer.parseInt(MainActivity.getnCh())<0){//in query2
            numChambre = " nombre_chambre >= 0";
        }
        if(Integer.parseInt(MainActivity.getCat())<=0){//in query 2
            categorie = " etoile >= "+ MainActivity.getCat();
        }
        if (Integer.parseInt(MainActivity.getMin()) < Integer.parseInt(MainActivity.getMax())) {// checks if the min is less than the max
            price = " prix >= " + MainActivity.getMin() + " AND prix <= " + MainActivity.getMax();
        }//maybe an esle statement for an invalid entry
        else{
            price = " prix >= 0 AND prix <= 9999999999";
        }
        try {
            if(dateFormat.parse(MainActivity.getArrival()).before(dateFormat.parse(MainActivity.getDeparture()))){//checks if the arrival is before departure
                date = " arrive >= '"+MainActivity.getArrival() +"' AND depart <= '"+ MainActivity.getDeparture()+"'";
            }
        }
        catch(Exception e){
            System.out.print("wrong date format");
        }


        String where = " WHERE "+ categorie +chaine ;


        //create temporary table later to be used by the hotel table
        query1 = "CREATE TEMPORARY TABLE temp AS\n"+
                "SELECT chambre.numero_chambre, chambre.prix, chambre.hadresse, chambre.superficie, chambre.capacite, location.arrive, location.depart\n " +
                "FROM chambre\n"+
                "\tINNER JOIN location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre\n" +
                "WHERE " + price + " AND " + date + capacite;
        System.out.println("query1: "+query1);

        //query returning the end result of the search
        query2 = "SELECT hnom, etoile, nombre_chambre, chaine_nom, temp.numero_chambre, temp.prix, temp.arrive, temp.depart, temp.capacite, temp.superficie\n" +
                "    FROM hotel\n" +
                "\tINNER JOIN temp ON temp.hadresse = hotel.hadresse" + where ;
        System.out.println("query2: "+query2);



        try {
            statement = connection.createStatement();

            boolean done = statement.execute(query1);
            resultSet = statement.executeQuery(query2);
            while (resultSet.next()) {
                double prix = resultSet.getDouble("prix");
                int cap = resultSet.getInt("capacite");
                double superf = resultSet.getDouble("superficie");
                int cat = resultSet.getInt("etoile");
                String ch = resultSet.getString("chaine_nom");
                String hnom = resultSet.getString("hnom");
                int nChambre = resultSet.getInt("nombre_chambre");
                System.out.println("executed:"+ done);

                dataList.add(new Test(ch, hnom, cat, cap, nChambre, prix, superf));
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