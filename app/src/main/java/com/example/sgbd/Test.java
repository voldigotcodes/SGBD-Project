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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }


    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }


    public double getSup() {
        return sup;
    }

    public void setSup(double sup) {
        this.sup = sup;
    }


    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }


    public String getChaine() {
        return cNom;
    }

    public void setChaine(String chaine) {
        this.cNom = chaine;
    }


    public int getNumCh() {
        return numCh;
    }

    public void setNumCh(int numCh) {
        this.numCh = numCh;
    }

}