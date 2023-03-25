package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view){

        Database db = new Database();
        Intent intent = new Intent(getApplicationContext(), Test.class);
        startActivity(intent);
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM testtable1";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int salary = resultSet.getInt("salary");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Salary: " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}