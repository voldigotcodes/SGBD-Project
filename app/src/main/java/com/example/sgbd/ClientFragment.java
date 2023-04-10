package com.example.sgbd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static List<Client> mDataList;
    private static ClientAdapter mAdapter;

    RecyclerView mRecyclerView;

    private View rootView;


    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_client, container, false);
        }

        mDataList = new ArrayList<>();

        // create adapter
        mAdapter = new ClientAdapter(mDataList);

        // get reference to RecyclerView
        mRecyclerView = rootView.findViewById(R.id.recyclerView);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
        new ClientDatabaseTask(mDataList, mAdapter).execute();

        FloatingActionButton addClient = rootView.findViewById(R.id.addClientButton);
        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rootView.getContext(), AddClient1.class);
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public static void showUpdatePopup(View view, String nom,
                                       String addy,
                                       int sin,
                                       String enregistrement, String email, String passeword) {
        // Create a MaterialDatePicker object for the date range picker\
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialogView = inflater.inflate(R.layout.popup_update_client, null);


        // Show the date range picker in a popup dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Update Client Information");


        EditText addressEdit = dialogView.findViewById(R.id.address);
        EditText nomEdit = dialogView.findViewById(R.id.nom);
        EditText emailEdit = dialogView.findViewById(R.id.email);
        EditText sinEdit = dialogView.findViewById(R.id.sin);
        EditText passewordEdit = dialogView.findViewById(R.id.passwordUpdate);
        EditText enregistrementEdit = dialogView.findViewById(R.id.enregistrementUpdate);
        Button deleteButton = dialogView.findViewById(R.id.deleteClient);

        addressEdit.setText(addy);
        nomEdit.setText(nom);
        sinEdit.setText(String.valueOf(sin));
        enregistrementEdit.setText(enregistrement);
        passewordEdit.setText(passeword);
        emailEdit.setText(email);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ClientFragment.DeleteClientTask(sin).execute();
                Toast.makeText(dialogView.getContext(), "You have succesfully deleted this employee, please refresh!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setPositiveButton("Confirm", (dialog, which) -> {
            // if the info has been filled out correctly then send it else return a toast
            if(!addressEdit.getText().toString().isEmpty() && !nomEdit.getText().toString().isEmpty()
                    && !sinEdit.getText().toString().isEmpty() && !enregistrementEdit.getText().toString().isEmpty()){
                new ClientFragment.UpdateClientTask(sin, addressEdit.getText().toString(), nomEdit.getText().toString(),
                        Integer.parseInt(sinEdit.getText().toString()), enregistrementEdit.getText().toString(), passewordEdit.getText().toString(), emailEdit.getText().toString()).execute();
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

    static class DeleteClientTask extends AsyncTask<Void, Void, Void> {
        int sin;


        public DeleteClientTask(int sin){
            this.sin = sin;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Database db = new Database();
            Connection connection = db.getConnection();
            Statement statement = null;
            String query = "DELETE FROM client WHERE client.sin_c = "+sin+"";
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
    static class UpdateClientTask extends AsyncTask<Void, Void, Void>{
        int oldSin;
        String addy;
        String nom;
        int sin;
        String enregistrement;
        String passeword;
        String email;

        public UpdateClientTask(int oldSin, String addy, String nom, int sin, String enregistrement, String passeword, String email) {
            this.oldSin = oldSin;
            this.addy = addy;
            this.nom = nom;
            this.sin = sin;
            this.enregistrement = enregistrement;
            this.passeword = passeword;
            this.email = email;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Database db = new Database();
            Connection connection = db.getConnection();
            Statement statement = null;
            String query = "UPDATE client SET cnom = '"+nom+"', sin_c = "+sin+", " +
                    "date_enregistrement = '"+enregistrement+"', cpassword = '"+passeword+"', client_email = '"+email+"', client_adresse = '"+addy+"' WHERE client.sin_c = "+oldSin+"";
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