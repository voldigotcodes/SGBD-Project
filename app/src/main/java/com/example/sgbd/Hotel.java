package com.example.sgbd;

public class Hotel {

    String cNom;
    String hNom;
    double cat;
    int cap;
    int numCh;
    double prix;
    double sup;
    String hadresse;
    String phone;
    String email;

    public Hotel(){}

    public Hotel(String cNom, String hNom, double cat, int cap, int numCh, double prix, double sup, String hadresse, String phone, String email) {
        this.cNom = cNom;
        this.hNom = hNom;
        this.cat = cat;
        this.cap = cap;
        this.numCh = numCh;
        this.prix = prix;
        this.sup = sup;
        this.hadresse = hadresse;
        this.phone = phone;
        this.email = email;
    }

    public String getHadresse() {
        return hadresse;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getcNom() {
        return cNom;
    }

    public String gethNom() {
        return hNom;
    }

    public double getCat() {
        return cat;
    }

    public int getCap() {
        return cap;
    }

    public int getNumCh() {
        return numCh;
    }

    public double getPrix() {
        return prix;
    }

    public double getSup() {
        return sup;
    }

    public String gethAd(){return hadresse;}
}