package com.example.sgbd;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<ReservationModel> mReservations;

    public ReservationAdapter(List<ReservationModel> reservations) {
        mReservations = reservations;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item_layout, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationModel reservation = mReservations.get(position);

        holder.id.setText(String.valueOf(reservation.getId()));
        holder.date_reservation.setText(String.valueOf(reservation.getDate_reservation()));
        holder.date_reserve.setText(String.valueOf(reservation.getDate_reserve()));
        holder.numero_chambre.setText(String.valueOf(reservation.getNumero_chambre()));
        holder.hAdresse.setText(String.valueOf(reservation.gethAdresse()));
        holder.sin_c.setText(String.valueOf(reservation.getSin_c()));
        holder.l_id.setText(String.valueOf(reservation.getL_id()));


    }

    @Override
    public int getItemCount() {
        if (mReservations == null) {
            return 0;
        }
        return mReservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView date_reservation;
        public TextView date_reserve;
        public TextView numero_chambre;
        public TextView sin_c;
        public TextView l_id;
        public TextView hAdresse;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idTextView);
            date_reservation = itemView.findViewById(R.id.date_reservationTextView);
            date_reserve = itemView.findViewById(R.id.date_reserveTextView);
            numero_chambre = itemView.findViewById(R.id.numero_chambreTextView);
            hAdresse = itemView.findViewById(R.id.hAdresseTextView);
            sin_c = itemView.findViewById(R.id.sin_cTextView);
            l_id = itemView.findViewById(R.id.l_idTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ReservationFragment.showUpdatePopup(view, id.getText().toString(), adresse.getText().toString(), Integer.parseInt(sin.getText().toString()),
                    //        hAdresse.getText().toString(), role.getText().toString(), email.getText().toString(), passeword);
                }
            });

        }
    }

}