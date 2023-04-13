package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddHotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
    }

    public void addHotel (View view){
        String adress = null;
        String nom = null;
        float etoiles = 0;
        String email = null;
        String nombreChambre = null;
        String phone = null;
        String chaine = null;

        EditText adressTextInput = findViewById(R.id.address);
        EditText nomTextInput = findViewById(R.id.nom);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText emailTextInput = findViewById(R.id.email);
        EditText nombreChambreTextInput = findViewById(R.id.nombreChambre);
        EditText phoneTextInput = findViewById(R.id.phone);
        EditText chaineTextInput = findViewById(R.id.nChaine);

        adress = adressTextInput.getText().toString();
        nom = nomTextInput.getText().toString();
        etoiles = ratingBar.getRating();
        email = emailTextInput.getText().toString();
        nombreChambre = nombreChambreTextInput.getText().toString();
        phone = phoneTextInput.getText().toString();
        chaine = chaineTextInput.getText().toString();

        new AddHotelDatabaseTask(view, adress, nom, etoiles, email, nombreChambre, phone, chaine).execute();
        Toast.makeText(view.getContext(), "Done!", Toast.LENGTH_SHORT).show();
    }
}

class AddHotelDatabaseTask extends AsyncTask<Void, Void, Void> {

    String adress;
    String nom;
    float etoiles;
    String email;
    String nombreChambre;
    String phone;
    String chaine;
    View view;

    public AddHotelDatabaseTask(View view, String adress, String nom, float etoiles, String email, String nombreChambre, String phone, String chaine) {
        this.adress = adress;
        this.nom = nom;
        this.etoiles = etoiles;
        this.email = email;
        this.nombreChambre = nombreChambre;
        this.phone = phone;
        this.chaine = chaine;
        this.view = view;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = null;
        String inJoin = "INNER JOIN";

        query = "insert into hotel values" +
                "('"+adress+"','"+nom+"',"+etoiles+",'"+email+"',"+nombreChambre+","+phone+",'"+chaine+"');\n";

        try {
            statement = connection.createStatement();
            statement.execute(query);
            System.out.println(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}