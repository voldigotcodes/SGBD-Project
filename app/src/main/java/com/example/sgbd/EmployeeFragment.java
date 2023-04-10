package com.example.sgbd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static List<Employee> eDataList;
    public static EmployeeAdapter eAdapter;

    RecyclerView eRecyclerView;

    private View rootView;


    public EmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_employee, container, false);
        }

        eDataList = new ArrayList<>();

        // create adapter
        eAdapter = new EmployeeAdapter(eDataList);


        // get reference to RecyclerView
        eRecyclerView = rootView.findViewById(R.id.recyclerView);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        eRecyclerView.setLayoutManager(layoutManager);

        eRecyclerView.setAdapter(eAdapter);
        new ManageEmployeeDatabaseTask(eDataList, eAdapter).execute();

        FloatingActionButton addEmployee = rootView.findViewById(R.id.addEmployeeButton);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rootView.getContext(), AddEmployee.class);
                startActivity(i);
            }
        });

        return rootView;
    }

    public static void showUpdatePopup(View view, String nom,
                                       String addy,
                                       int sin,
                                       String hAdresse,
                                       String role, String email, String passeword) {
        // Create a MaterialDatePicker object for the date range picker\
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialogView = inflater.inflate(R.layout.popup_update_employee, null);


        // Show the date range picker in a popup dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Update Employee Information");


        EditText addressEdit = dialogView.findViewById(R.id.address);
        EditText nomEdit = dialogView.findViewById(R.id.nom);
        EditText emailEdit = dialogView.findViewById(R.id.email);
        EditText sinEdit = dialogView.findViewById(R.id.sin);
        EditText passewordEdit = dialogView.findViewById(R.id.passwordUpdate);
        EditText hAdresseEdit = dialogView.findViewById(R.id.hAdresseUpdate);
        EditText roleEdit = dialogView.findViewById(R.id.roleUpdate);
        Button deleteButton = dialogView.findViewById(R.id.deleteEmployee);

        addressEdit.setText(addy);
        nomEdit.setText(nom);
        sinEdit.setText(String.valueOf(sin));
        hAdresseEdit.setText(hAdresse);
        roleEdit.setText(role);
        passewordEdit.setText(passeword);
        emailEdit.setText(email);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteEmployeeTask(sin).execute();
                Toast.makeText(dialogView.getContext(), "You have succesfully deleted this employee, please refresh!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setPositiveButton("Confirm", (dialog, which) -> {
            // if the info has been filled out correctly then send it else return a toast
            if(!addressEdit.getText().toString().isEmpty() && !nomEdit.getText().toString().isEmpty()
                    && !sinEdit.getText().toString().isEmpty() && !hAdresseEdit.getText().toString().isEmpty()){
                new UpdateEmployeeTask(sin, addressEdit.getText().toString(), nomEdit.getText().toString(),
                        Integer.parseInt(sinEdit.getText().toString()), hAdresseEdit.getText().toString(),
                        roleEdit.getText().toString(), passewordEdit.getText().toString(), emailEdit.getText().toString()).execute();
                Toast.makeText(dialogView.getContext(), "You succuesfully updated this employeee", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(dialogView.getContext(), "You have not filled out the form correctly", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do something when the "Cancel" button is clicked
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    static class DeleteEmployeeTask extends AsyncTask<Void, Void, Void> {
        int sin;


        public DeleteEmployeeTask(int sin){
            this.sin = sin;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Database db = new Database();
            Connection connection = db.getConnection();
            Statement statement = null;
            String query = "DELETE FROM employe WHERE employe.sin_e = "+sin+"";
            System.out.println(query);
            try {
                statement = connection.createStatement();
                statement.execute(query);
                db.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    static class UpdateEmployeeTask extends AsyncTask<Void, Void, Void>{
        int oldSin;
        String addy;
        String nom;
        int sin;
        String hAdresse;
        String role;
        String passeword;
        String email;

        public UpdateEmployeeTask(int oldSin, String addy, String nom, int sin, String hAdresse, String role, String passeword, String email) {
            this.oldSin = oldSin;
            this.addy = addy;
            this.nom = nom;
            this.sin = sin;
            this.hAdresse = hAdresse;
            this.role = role;
            this.passeword = passeword;
            this.email = email;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Database db = new Database();
            Connection connection = db.getConnection();
            Statement statement = null;
            String query = "UPDATE employe SET enom = '"+nom+"', sin_e = "+sin+", " +
                    "hadresse = '"+hAdresse+"', role = '"+role+"', epassword = '"+passeword+"', employe_email = '"+email+"', eadresse = '"+addy+"' WHERE employe.sin_e = "+oldSin+"";
            System.out.println(query);
            try {
                statement = connection.createStatement();
                statement.execute(query);
                db.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}