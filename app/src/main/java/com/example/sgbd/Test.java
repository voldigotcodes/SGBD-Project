package com.example.sgbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Test {

    double prix;
    double sup;
    int cap;
    String cat;
    String chaine;
    String numCh;

    public Test(){}

    public Test(double prix, int cap, double sup) {
        this.prix = prix;
        this.sup = sup;
        this.cap = cap;


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

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public String getNumCh() {
        return numCh;
    }

    public void setNumCh(String numCh) {
        this.numCh = numCh;
    }

}