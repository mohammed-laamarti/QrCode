package com.myapp.qrcode.dto;

import java.time.LocalDate;

public class SignatureResponse {
    private String nom;
    private String prenom;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;


    public SignatureResponse(String nom, String prenom, LocalDate dateCreation, LocalDate dateExpiration) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateCreation = dateCreation;
        this.dateExpiration = dateExpiration;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}