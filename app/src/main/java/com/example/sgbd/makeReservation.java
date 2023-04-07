package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class makeReservation extends AppCompatActivity {
    public static List<Test> mDataList;
    public static TestAdapter mAdapter;
    public static EditText nom, sin, ady, date, pass, email, arr, dep;
    public static TextView nch, adH;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        sin = (EditText) findViewById(R.id.sinEdit);
        nom = (EditText) findViewById(R.id.nomClient);
        ady = (EditText) findViewById(R.id.addy);
        date = (EditText) findViewById(R.id.date);
        pass = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.emailCLient);
        arr = (EditText) findViewById(R.id.arr);
        dep = (EditText) findViewById(R.id.dep);
        nch = (TextView) findViewById(R.id.nChambre);
        adH = (TextView) findViewById(R.id.hotAd);

        Intent intent = getIntent();
        String nChambre = intent.getStringExtra("No. chambre");
        String adHotel = intent.getStringExtra("Adresse");
        System.out.println("num de Chambre: "+nChambre);
        nch.setText(nChambre);
        adH.setText(adHotel);
    }

    public static String getNch(){
        return nch.getText().toString();
    }
    public static String getAdH(){
        return adH.getText().toString();
    }
    public static String getArrival() {
        String arrival = arr.getText().toString(); // get the text entered by the user as a String
        return arrival;
    }

    public static String getDeparture() {
        String depart = dep.getText().toString(); // get the text entered by the user as a String
        return depart;
    }

    public static String getSin() {
        String sin_c = sin.getText().toString();
        return sin_c;
    }

    public static String getNom() {
        String name = nom.getText().toString();
        return name;
    }

    public static String getDate() {
        String d = date.getText().toString();
        return d;
    }
    public static String getPass(){
        String password = pass.getText().toString();
        return password;
    }
    public static String getEmail(){
        String em = email.getText().toString();
        return em;
    }
    public static String getAdy(){
        String ad = ady.getText().toString();
        return ad;
    }

    public void save(View view) {
        new ReservationDatabaseTask(mDataList).execute();
        Toast toast = Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

    class ReservationDatabaseTask extends AsyncTask<Void, Void, List<Test>> {

        private List<Test> mDataList;

        public ReservationDatabaseTask(List<Test> dataList) {
            mDataList = dataList;
        }

        @Override
        protected List<Test> doInBackground(Void... voids) {
            List<Test> dataList = new ArrayList<>();

            Database db = new Database();
            Connection connection = db.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            String nom = makeReservation.getNom();
            String sin= makeReservation.getSin();
            String address = makeReservation.getAdy();
            String date= null;
            String password = makeReservation.getPass();
            String email = makeReservation.getEmail();
            String arr = makeReservation.getArrival();
            String dep = makeReservation.getDeparture();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String query1;
            String query2;

            try {
                if(dateFormat.parse(makeReservation.getArrival()).before(dateFormat.parse(makeReservation.getDeparture()))){//checks if the arrival is before departure
                    date = makeReservation.getDate();
                }
            }
            catch(Exception e){
                System.out.print("wrong date format");
            }

            String query0 = "SELECT COUNT(*) FROM client\n" +
                    "Where sin_c = "+sin;
            System.out.println("query0: "+query0);
            int count =0;
            try {
                statement =connection.createStatement();
                resultSet = statement.executeQuery(query0);
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(count==0){
                query0 = "Insert into client \n" +
                        "values ("+sin + ", '"+nom+"', "+ "'"+address+"', " + "'"+date+"', " +"'"+password+"', '"+email+"')";
                try {
                    statement =connection.createStatement();
                    statement.executeQuery(query0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("query01: "+query0);
            }

            Random rand = new Random();
            int randomNumber = rand.nextInt(99999999);


            query1 = "INSERT into reservation\n"+
                    "values ("+randomNumber +", '"+date+"', '"+arr+"', "+makeReservation.getNch() +", "+sin+", "+null+", '"+makeReservation.getAdH()+"')";
            System.out.println("query1: "+query1);



            try {
                statement = connection.createStatement();


                statement.execute(query1);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dataList;
        }


}