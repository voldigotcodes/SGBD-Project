package com.example.sgbd;

public class LocationModel {
    int id;
    String arrive;
    String depart;
    int numero_chambre;
    int sin_c;
    int sin_e;
    String hAdresse;

    public LocationModel(int id, String arrive, String depart, int numero_chambre, int sin_c, int sin_e, String hAdresse) {
        this.id = id;
        this.arrive = arrive;
        this.depart = depart;
        this.numero_chambre = numero_chambre;
        this.sin_c = sin_c;
        this.sin_e = sin_e;
        this.hAdresse = hAdresse;
    }

    public int getId() {
        return id;
    }

    public String getArrive() {
        return arrive;
    }

    public String getDepart() {
        return depart;
    }

    public int getNumero_chambre() {
        return numero_chambre;
    }

    public int getSin_c() {
        return sin_c;
    }

    public int getSin_e() {
        return sin_e;
    }

    public String gethAdresse() {
        return hAdresse;
    }
}
