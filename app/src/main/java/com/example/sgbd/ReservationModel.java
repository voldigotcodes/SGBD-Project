package com.example.sgbd;

public class ReservationModel {
    String id;
    String date_reservation;
    String date_reserve;
    int numero_chambre;
    String sin_c;
    String l_id;
    String hAdresse;

    public ReservationModel(String id, String date_reservation, String date_reserve, int numero_chambre, String sin_c, String l_id, String hAdresse) {
        this.id = id;
        this.date_reservation = date_reservation;
        this.date_reserve = date_reserve;
        this.numero_chambre = numero_chambre;
        this.sin_c = sin_c;
        this.l_id = l_id;
        this.hAdresse = hAdresse;
    }

    public String getId() {
        return id;
    }

    public String getDate_reservation() {
        return date_reservation;
    }

    public String getDate_reserve() {
        return date_reserve;
    }

    public int getNumero_chambre() {
        return numero_chambre;
    }

    public String getSin_c() {
        return sin_c;
    }

    public String getL_id() {
        return l_id;
    }

    public String gethAdresse() {
        return hAdresse;
    }
}
