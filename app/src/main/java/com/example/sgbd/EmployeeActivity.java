package com.example.sgbd;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeActivity extends AppCompatActivity {

    public static List<Hotel> mDataList;
    public static HotelAdapter mAdapter;
    public static EditText min, max, arriv, dep, capacity, area, cHot, cat, numCh;

    RecyclerView mRecyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        min = (EditText) findViewById(R.id.editMinPriceEmp);
        max = (EditText) findViewById(R.id.editMaxPriceEmp);
        arriv = (EditText)findViewById(R.id.editTextArrivalEmp);
        dep = (EditText) findViewById(R.id.editTextDepEmp);
        capacity = (EditText) findViewById(R.id.capacityEditTextEmp);
        area = (EditText) findViewById(R.id.sizeEditTextEmp);
        cHot = (EditText) findViewById(R.id.chaineEditTextEmp);
        cat = (EditText) findViewById(R.id.categoryEditTextEmp);
        numCh = (EditText) findViewById(R.id.capacityEditTextEmp);
//have to set up the rest of the editText variables
        Button hideShow = findViewById(R.id.revealBtnEmp);

        hideShow.setText("SHOW FILTERS");


        mDataList = new ArrayList<>();

        // create adapter
        mAdapter = new HotelAdapter(mDataList);

        // get reference to RecyclerView
        mRecyclerView = findViewById(R.id.hotelList);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // set adapter
        mRecyclerView.setAdapter(mAdapter);

        //Fetch all the data the first time
        new EmployeeDatabaseTask(mDataList, mAdapter).execute();

    }

    public void filterReveal(View view){
        Button hideShow = findViewById(R.id.revealBtnEmp);
        View filters = findViewById(R.id.filters);
        ViewGroup.LayoutParams params = filters.getLayoutParams();
        if(hideShow.getText().toString().toUpperCase(Locale.ROOT).startsWith("H")) {
            params.height = findViewById(R.id.searchLinearLayoutE).getHeight();
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
            arrival = "0001/01/01"; // set a default value if the EditText is empty
        }
        return arrival;
    }
    public static String getDeparture(){
        String depart = dep.getText().toString(); // get the text entered by the user as a String
        if (depart.isEmpty()) {
            depart = "9999/12/31"; // set a default value if the EditText is empty
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


    public void testEmp(View view){

        new EmployeeDatabaseTask(mDataList, mAdapter).execute();
        Toast toast = Toast.makeText(getApplicationContext(),"MIN: "+getMin()+ " MAX: "+getMax(), Toast.LENGTH_SHORT);
        toast.show();

    }

    public static void showUpdateHotelPopup(View view, String addy,
                                            String nom,
                                            String nombreChambre,
                                            String nChaine,
                                            int ratingBar2, String phone, String email) {
        // Create a MaterialDatePicker object for the date range picker\
        FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialogView = inflater.inflate(R.layout.popup_update_hotel, null);

        // Set the date selection listener to update the UI when the user selects a date range
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert the selected dates to a formatted string for display in the UI
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateRangeString = dateFormat.format(new Date(selection.first)) + " - " + dateFormat.format(new Date(selection.second));
            // Update the UI with the selected date range
        });


        // Show the date range picker in a popup dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Update Room Information");


        EditText addressEdit = dialogView.findViewById(R.id.address);
        EditText nomEdit = dialogView.findViewById(R.id.nom);
        EditText emailEdit = dialogView.findViewById(R.id.email);
        EditText nombreChambreEdit = dialogView.findViewById(R.id.nombreChambre);
        EditText phoneEdit = dialogView.findViewById(R.id.phone);
        EditText nChaineEdit = dialogView.findViewById(R.id.nChaine);
        RatingBar ratingBar2Edit = dialogView.findViewById(R.id.ratingBar2);
        Button deleteButton = dialogView.findViewById(R.id.deleteHotel);

        addressEdit.setText(addy);
        nomEdit.setText(nom);
        nombreChambreEdit.setText(nombreChambre);
        nChaineEdit.setText(nChaine);
        ratingBar2Edit.setRating(ratingBar2);
        phoneEdit.setText(phone);
        emailEdit.setText(email);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteHotelTask(addressEdit.getText().toString()).execute();
                Toast.makeText(dialogView.getContext(), "You have succesfully deleted this hotel, please refresh!", Toast.LENGTH_SHORT).show();

            }
        });

        alertDialogBuilder.setPositiveButton("Confirm", (dialog, which) -> {
            // if the info has been filled out correctly then send it else return a toast
            if(!addressEdit.getText().toString().isEmpty() && !nomEdit.getText().toString().isEmpty()
                    && !nombreChambreEdit.getText().toString().isEmpty() && !nChaineEdit.getText().toString().isEmpty()){
                new UpdateHotelTask(addy, addressEdit.getText().toString(), nomEdit.getText().toString(),
                        nombreChambreEdit.getText().toString(), nChaineEdit.getText().toString(),
                        (int) ratingBar2Edit.getRating(), phoneEdit.getText().toString(), emailEdit.getText().toString()).execute();
                Toast.makeText(dialogView.getContext(), "You succuesfully updated this hotel", Toast.LENGTH_SHORT).show();
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

    public void onClickManageReservation(View view){
        Intent i = new Intent(getApplicationContext(), ManageBookings.class);
        startActivity(i);
    }

    public void onClickManagePeople(View view){
        Intent i = new Intent(getApplicationContext(), ShowClients.class);
        startActivity(i);
    }

    public void onClickAddHotel(View view){
        Intent i = new Intent(getApplicationContext(), AddHotelActivity.class);
        startActivity(i);
    }


}
class DeleteHotelTask extends AsyncTask<Void, Void, Void>{
    String addy;

    public DeleteHotelTask(String addy) {
        this.addy = addy;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        String query = "DELETE FROM hotel WHERE hotel.hadresse = '"+addy+"'";
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
class UpdateHotelTask extends AsyncTask<Void, Void, Void>{
    String oldAddy;
    String addy;
    String nom;
    String nombreChambre;
    String nChaine;
    int ratingBar2;
    String phone;
    String email;

    public UpdateHotelTask(String oldAddy, String addy, String nom, String nombreChambre, String nChaine, int ratingBar2, String phone, String email) {
        this.oldAddy = oldAddy;
        this.addy = addy;
        this.nom = nom;
        this.nombreChambre = nombreChambre;
        this.nChaine = nChaine;
        this.ratingBar2 = ratingBar2;
        this.phone = phone;
        this.email = email;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        String query = "UPDATE hotel SET hnom = '"+nom+"', nombre_chambre = '"+nombreChambre+"', " +
                "chaine_nom = '"+nChaine+"', etoile = "+ratingBar2+", hphone_number = '"+phone+"', hemail = '"+email+"', hadresse = '"+addy+"'"+" WHERE hotel.hadresse = '"+oldAddy+"'";
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
class EmployeeDatabaseTask extends AsyncTask<Void, Void, List<Hotel>> {

    private List<Hotel> mDataList;
    private HotelAdapter adapter;

    public EmployeeDatabaseTask(List<Hotel> dataList, HotelAdapter adapter) {
        mDataList = dataList;
        this.adapter = adapter;
    }

    @Override
    protected List<Hotel> doInBackground(Void... voids) {
        List<Hotel> dataList = new ArrayList<>();

        Database db = new Database();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String price = " prix >= " + EmployeeActivity.getMin() + " AND prix <= " + EmployeeActivity.getMax();
        String date = null;
        String capacite=  capacite = " AND chambre.capacite >= "+ EmployeeActivity.getCap();
        String superficie =" AND chambre.superficie >= "+ EmployeeActivity.getArea();
        String chaine = "";
        String numChambre = null;
        String categorie = " etoile >= '"+ EmployeeActivity.getCat()+"'";
        String where ="";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String query1;
        String query2;

        if(!EmployeeActivity.getChot().equals("")){//in query2
            chaine = " AND chaine_nom = '"+EmployeeActivity.getChot()+"'";
        }

        if(Integer.parseInt(EmployeeActivity.getCap())<0){
            capacite = " AND chambre.capacite >= 0";
        }


        if(Double.parseDouble(EmployeeActivity.getArea())<0){
            superficie = " AND chambre.superficie >= 0";
        }

        if(Integer.parseInt(EmployeeActivity.getnCh())<0){//in query2
            numChambre = " nombre_chambre >= 0";
        }
        if(Integer.parseInt(EmployeeActivity.getCat())<=0){//in query 2
            categorie = " etoile >= "+ EmployeeActivity.getCat();
        }
        if (Integer.parseInt(EmployeeActivity.getMin()) < Integer.parseInt(EmployeeActivity.getMax())) {// checks if the min is less than the max
            price = " prix >= " + EmployeeActivity.getMin() + " AND prix <= " + EmployeeActivity.getMax();
        }//maybe an esle statement for an invalid entry
        else{
            price = " prix >= 0 AND prix <= 9999999999";
        }
        try {
            if(dateFormat.parse(EmployeeActivity.getArrival()).before(dateFormat.parse(EmployeeActivity.getDeparture()))){//checks if the arrival is before departure
                date = " AND '"+EmployeeActivity.getArrival() +"'> depart AND '"+ EmployeeActivity.getDeparture()+"'< arrive"+ " OR (arrive IS NULL AND depart IS NULL)";
                //AND '2023-12-31'> depart AND '2022-12-31'< arrive OR (arrive IS NULL AND depart IS NULL)
            }
        }
        catch(Exception e){
            System.out.print("wrong date format");
        }


        where = " WHERE "+ categorie +chaine ;


        //create temporary table later to be used by the hotel table
//         CREATE TEMPORARY TABLE temp AS
//SELECT chambre.numero_chambre, chambre.prix, chambre.hadresse, chambre.superficie, chambre.capacite, location.arrive, location.depart
//    FROM chambre
//	Left JOIN location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre
//	WHERE prix >=500 AND prix <= 2000 AND '2023-12-31'> depart AND '2022-12-31'< arrive OR (arrive IS NULL AND depart IS NULL)
//        System.out.println("query1: "+query1);
//
//        //query returning the end result of the search
//        query2 = "SELECT hnom, etoile, nombre_chambre, chaine_nom, temp.numero_chambre, temp.prix, temp.arrive, temp.depart, temp.capacite, temp.superficie\n" +
//                "    FROM hotel\n" +
//                "\tINNER JOIN temp ON temp.hadresse = hotel.hadresse" + where ;
        query1 = "CREATE TEMPORARY TABLE temp AS\n"+
                "SELECT chambre.numero_chambre, chambre.prix, chambre.hadresse, chambre.superficie, chambre.capacite\n " +
                "FROM chambre\n"+
                "\tLeft JOIN location ON chambre.hadresse = location.hadresse AND chambre.numero_chambre = location.numero_chambre\n"+
                "WHERE " + price + date + capacite + superficie;
        System.out.println("query1: "+query1);

        //query returning the end result of the search
        query2 = "SELECT hnom, etoile, nombre_chambre, chaine_nom, temp.numero_chambre, temp.prix, temp.capacite, temp.superficie, hotel.hadresse, hotel.hphone_number, hotel.hemail\n" +
                "    FROM hotel\n" +
                "\tINNER JOIN temp ON temp.hadresse = hotel.hadresse" + where +" AND "+ price;
        System.out.println("query2: "+query2);
        System.out.println("nch: " + EmployeeActivity.getCap());



        try {
            statement = connection.createStatement();

            boolean done = statement.execute(query1);
            resultSet = statement.executeQuery(query2);
            while (resultSet.next()) {
                String ad = resultSet.getString("hadresse");
                double prix = resultSet.getDouble("prix");
                int cap = resultSet.getInt("capacite");
                double superf = resultSet.getDouble("superficie");
                double cat = resultSet.getDouble("etoile");
                String ch = resultSet.getString("chaine_nom");
                String hnom = resultSet.getString("hnom");
                int nChambre = resultSet.getInt("nombre_chambre");
                String email = resultSet.getString("hemail");
                String phone = resultSet.getString("hphone_number");
                System.out.println("executed:"+ done);

                dataList.add(new Hotel(ch, hnom, cat, cap, nChambre, prix, superf, ad, phone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Override
    protected void onPostExecute(List<Hotel> dataList) {
        super.onPostExecute(dataList);
        mDataList.clear();
        mDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }
}