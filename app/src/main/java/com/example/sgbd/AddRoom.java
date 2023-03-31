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

public class AddRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
    }

    public void addRoom(View view){
        String adress = null;
        String num = null;
        String prix = null;
        String capacite = null;
        String vue = null;
        String superficie = null;
        String extansion = null;
        String damage = null;

        EditText adresse = findViewById(R.id.ad);
        EditText numero = findViewById(R.id.numCh);
        EditText price = findViewById(R.id.prix);
        EditText capacity = findViewById(R.id.cap);
        EditText vuee = findViewById(R.id.vue);
        EditText supe = findViewById(R.id.sup);
        EditText extension = findViewById(R.id.ext);
        EditText dam = findViewById(R.id.dommage);

        adress = adresse.getText().toString();
        num = numero.getText().toString();
        prix = price.getText().toString();
        capacite = capacity.getText().toString();
        vue = vuee.getText().toString();
        superficie = supe.getText().toString();
        extansion = extension.getText().toString();
        damage = dam.getText().toString();

        new AddRoomDatabaseTask(view, adress, num, prix, capacite, vue, superficie, extansion, damage).execute();
        Toast.makeText(view.getContext(), "Done!", Toast.LENGTH_SHORT).show();
    }
}

class AddRoomDatabaseTask extends AsyncTask<Void, Void, Void> {

    String adress;
    String num;
    String prix;
    String capacite;
    String vue;
    String superficie;
    String extansion;
    String damage;
    View view;

    public AddRoomDatabaseTask(View view, String adress, String num, String prix, String capacite, String vue, String superficie, String extansion, String damage) {
        this.adress = adress;
        this.num = num;
        this.prix = prix;
        this.capacite = capacite;
        this.vue = vue;
        this.superficie = superficie;
        this.extansion = extansion;
        this.damage = damage;
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

        query = "insert into chambre values" +
                "('"+num+"',"+prix+","+capacite+",'"+vue+"',"+superficie+",'"+extansion+"','"+damage+"','"+adress+"');\n";

        try {
            statement = connection.createStatement();
            statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}