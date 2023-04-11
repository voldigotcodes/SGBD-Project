package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddClient1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client1);
    }

    public void addClient (View view){
        String sin = null;
        String nom = null;
        String cAddress = null;
        String  date = null;
        String cpass = null;
        String cemail = null;

        EditText cadresse = findViewById(R.id.role);
        EditText csin = findViewById(R.id.sinE);
        EditText  cnom= findViewById(R.id.enom);
        EditText clpass = findViewById(R.id.hAd);
        EditText cdate = findViewById(R.id.eAd);
        EditText emailC = findViewById(R.id.emailE);


        cAddress = cadresse.getText().toString();
        nom = cnom.getText().toString();
        cpass = clpass.getText().toString();
        cemail = emailC.getText().toString();
        sin = csin.getText().toString();
        date = cdate.getText().toString();
        new AddClientDatabaseTask(view, sin, nom, cAddress, date, cpass, cemail).execute();
        Toast.makeText(view.getContext(), "Done!", Toast.LENGTH_SHORT).show();
    }
}

class AddClientDatabaseTask extends AsyncTask<Void, Void, Void> {

    String sin;
    String nom;
    String address;
    String  date;
    String pass;
    String email;
    View view;

    public AddClientDatabaseTask(View view, String sin, String nom, String address, String date, String pass, String email) {
        this.sin = sin;
        this.nom = nom;
        this.address = address;
        this.date = date;
        this.pass = pass;
        this.email = email;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = null;
        String inJoin = "INNER JOIN";

        query = "insert into client values" +
                "('"+sin+"','"+nom+"',"+address+",'"+date+"','"+pass+"','"+email+"');\n";

        try {
            statement = connection.createStatement();
            statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}