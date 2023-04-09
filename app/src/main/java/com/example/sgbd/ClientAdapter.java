package com.example.sgbd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> mClients;

    public ClientAdapter(List<Client> clients) {
        mClients = clients;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item_layout, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = mClients.get(position);

        holder.nom.setText(String.valueOf(client.getNom()));
        holder.sin.setText(String.valueOf(client.getSin()));
        holder.adresse.setText(String.valueOf(client.getAdresse()));
        holder.enregistrement.setText(String.valueOf(client.getDate_enregistrement()));
        holder.email.setText(String.valueOf(client.getEmail()));
        holder.passeword = client.getPasseword();


    }

    @Override
    public int getItemCount() {
        if (mClients == null) {
            return 0;
        }
        return mClients.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public TextView sin;
        public TextView adresse;
        public TextView enregistrement;
        public TextView email;
        public String passeword;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nomTextView);
            sin = itemView.findViewById(R.id.sinTextView);
            adresse = itemView.findViewById(R.id.adresseTextView);
            enregistrement = itemView.findViewById(R.id.enregistrementTextView);
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