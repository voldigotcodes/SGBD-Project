package com.example.sgbd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> mEmployees;

    public EmployeeAdapter(List<Employee> emmployees) {
        mEmployees = emmployees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item_layout, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = mEmployees.get(position);

        holder.nom.setText(String.valueOf(employee.getNom()));
        holder.sin.setText(String.valueOf(employee.getSin()));
       // holder.role.setText(String.valueOf(employee.getRole()));
        holder.adresse.setText(String.valueOf(employee.getAdresse()));
       // holder.hAdresse.setText(String.valueOf(employee.gethAdresse()));
        holder.email.setText(String.valueOf(employee.getEmail()));
        holder.passeword = employee.getPasseword();


    }

    @Override
    public int getItemCount() {
        if (mEmployees == null) {
            return 0;
        }
        return mEmployees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public TextView sin;
        public TextView role;
        public TextView adresse;
        public TextView hAdresse;
        public TextView email;
        public String passeword;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nomTextView);
            sin = itemView.findViewById(R.id.sinTextView);
           // role = itemView.findViewById(R.id.roleTextView);
            adresse = itemView.findViewById(R.id.adresseTextView);
            //hAdresse = itemView.findViewById(R.id.hadresseTextView);
            email = itemView.findViewById(R.id.emailTextView);
            passeword = "";

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }
    }

}