package com.example.sgbd;

public class Employee {
    String nom;
    String sin;
    String role;
    String adresse;
    String hAdresse;
    String passeword;
    String email;

    public Employee(String nom, String sin, String role, String adresse, String hAdresse, String passeword, String email) {
        this.nom = nom;
        this.sin = sin;
        this.role = role;
        this.adresse = adresse;
        this.hAdresse = hAdresse;
        this.passeword = passeword;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getSin() {
        return sin;
    }

    public String getRole() {
        return role;
    }

    public String getAdresse() {
        return adresse;
    }

    public String gethAdresse() {
        return hAdresse;
    }

    public String getPasseword() {
        return passeword;
    }

    public String getEmail() {
        return email;
    }
}
