package com.example.sgbd;

public class Client {
    String nom;
    String sin;
    String adresse;
    String date_enregistrement;
    String passeword;
    String email;

    public Client(String nom, String sin, String adresse, String date_enregistrement, String passeword, String email) {
        this.nom = nom;
        this.sin = sin;
        this.adresse = adresse;
        this.date_enregistrement = date_enregistrement;
        this.passeword = passeword;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getSin() {
        return sin;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDate_enregistrement() {
        return date_enregistrement;
    }

    public String getPasseword() {
        return passeword;
    }

    public String getEmail() {
        return email;
    }
}