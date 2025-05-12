package com.myapp.qrcode.validation;

import com.myapp.qrcode.dto.CreateSignatureRequest;

public class CreateSignatureRequestValidator {
    public static void validate(CreateSignatureRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête ne doit pas être null.");
        }
        if (request.getNom() == null || request.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (request.getPrenom() == null || request.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }
    }
}
