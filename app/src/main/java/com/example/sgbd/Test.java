package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Test {

    String cNom;
    String hNom;
    double cat;
    int cap;
    int numCh;
    double prix;
    double sup;
    String hadresse;

    public Test(){}

    public Test(String cNom, String hNom, double cat, int cap, int numch, double prix, double sup, String hadresse) {
        this.cNom = cNom;
        this.hNom = hNom;
        this.cat = cat;
        this.cap = cap;
        this.numCh = numch;
        this.prix = prix;
        this.sup = sup;
        this.hadresse = hadresse;

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