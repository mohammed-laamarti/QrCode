package com.myapp.qrcode.model;

import java.time.LocalDate;

public class SignatureData {
    private String nom;
    private String prenom;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;

    public SignatureData(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateCreation = LocalDate.now();
        this.dateExpiration = dateCreation.plusMonths(6); ;
    }
    public SignatureData(){
    }



    public boolean isExpired() {
        return LocalDate.now().isAfter(dateExpiration) ||
                LocalDate.now().isEqual(dateExpiration);
    }

    public String toEncodedString() {
        return String.join("|",
                nom,
                prenom,
                dateCreation.toString(),
                dateExpiration.toString()
        );
    }

    public static SignatureData fromString(String data) {
        String[] parts = data.split("\\|");
        return new SignatureData(
                parts[0],
                parts[1]
        );
    }



    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
