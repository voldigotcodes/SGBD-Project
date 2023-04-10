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

public class AddEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
    }
    public void addEmp (View view){
        String sine = null;
        String enom = null;
        String role = null;
        String eAddress = null;
        String hadresse = null;
        String epass = null;
        String eemail = null;

        EditText r  = findViewById(R.id.role);
        EditText esin = findViewById(R.id.sinE);
        EditText eNom= findViewById(R.id.enom);
        EditText hotelAd = findViewById(R.id.hAd);
        EditText eadresse  = findViewById(R.id.eAd);
        EditText emailE = findViewById(R.id.emailE);
        EditText passE = findViewById(R.id.Epass);


        eAddress = eadresse.getText().toString();
        enom = eNom.getText().toString();
        epass = passE.getText().toString();
        eemail = emailE.getText().toString();
        sine = esin.getText().toString();
        role = r.getText().toString();
        hadresse = hotelAd.getText().toString();
        new AddEmpDatabaseTask(view, sine, enom, role, eAddress, hadresse, epass, eemail).execute();
        Toast.makeText(view.getContext(), "Done!", Toast.LENGTH_SHORT).show();
    }
}

class AddEmpDatabaseTask extends AsyncTask<Void, Void, Void> {

    String sine = null;
    String enom = null;
    String role = null;
    String eAddress = null;
    String hadresse = null;
    String epass = null;
    String eemail = null;

    public AddEmpDatabaseTask(View view, String sine, String enom, String role, String eAddress, String hadresse, String epass, String eemail) {
        this.sine = sine;
        this.enom = enom;
        this.role = role;
        this.hadresse = hadresse;
        this.eAddress = eAddress;
        this.epass = epass;
        this.eemail = eemail;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = null;
        String inJoin = "INNER JOIN";

        query = "insert into employe values" +
                "("+sine+",'"+enom+"','"+role+"','"+eAddress+"','"+hadresse+"','"+epass+"','"+eemail+"');\n";

        try {
            statement = connection.createStatement();
            statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}