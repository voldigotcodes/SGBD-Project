package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Test {

    String cNom;
    String hNom;
    int cat;
    int cap;
    int numCh;
    double prix;
    double sup;

    public Test(){}

    public Test(String cNom, String hNom, int cat, int cap, int numch, double prix, double sup) {
        this.cNom = cNom;
        this.hNom = hNom;
        this.cat = cat;
        this.cap = cap;
        this.numCh = numch;
        this.prix = prix;
        this.sup = sup;

    }

    public String getcNom() {
        return cNom;
    }

    public String gethNom() {
        return hNom;
    }

    public int getCat() {
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
}