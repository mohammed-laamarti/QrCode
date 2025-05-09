package com.myapp.qrcode.model;

import java.time.LocalDate;

public class SignatureData {
    private String nom;
    private String prenom;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;

    public SignatureData(String nom, String prenom, LocalDate dateExpiration) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateCreation = LocalDate.now();
        this.dateExpiration = dateExpiration;
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
                parts[1],
                LocalDate.parse(parts[3])
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
}
