package com.example.sgbd;

import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<LocationModel> mLocations;

    public LocationAdapter(List<LocationModel> locations) {
        mLocations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationModel location = mLocations.get(position);

        holder.id.setText(String.valueOf(location.getId()));
        holder.arrive.setText(String.valueOf(location.getArrive()));
        holder.depart.setText(String.valueOf(location.getDepart()));
        holder.numero_chambre.setText(String.valueOf(location.getNumero_chambre()));
        holder.hAdresse.setText(String.valueOf(location.gethAdresse()));
        holder.sin_c.setText(String.valueOf(location.getSin_c()));
        holder.sin_e.setText(String.valueOf(location.getSin_e()));


    }

    @Override
    public int getItemCount() {
        if (mLocations == null) {
            return 0;
        }
        return mLocations.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView arrive;
        public TextView depart;
        public TextView numero_chambre;
        public TextView sin_c;
        public TextView sin_e;
        public TextView hAdresse;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idTextView);
            arrive = itemView.findViewById(R.id.arriveTextView);
            depart = itemView.findViewById(R.id.departTextView);
            numero_chambre = itemView.findViewById(R.id.numero_chambreTextView);
            hAdresse = itemView.findViewById(R.id.hAdresseTextView);
            sin_c = itemView.findViewById(R.id.sin_cTextView);
            sin_e = itemView.findViewById(R.id.sin_eTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LocationFragment.showUpdatePopup(view, id.getText().toString(), adresse.getText().toString(), Integer.parseInt(sin.getText().toString()),
                    //        hAdresse.getText().toString(), role.getText().toString(), email.getText().toString(), passeword);
                }
            });

        }
    }

}