package com.myapp.qrcode.service;

import com.myapp.qrcode.mapper.SignatureMapper;
import com.myapp.qrcode.model.SignatureData;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Base64;


import com.myapp.qrcode.dto.CreateSignatureRequest;
import com.myapp.qrcode.dto.SignatureResponse;

import java.time.LocalDate;


@Service
public class SignatureService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public SignatureService() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // Méthode pour signer les données à partir d’un DTO
    public String signData(CreateSignatureRequest request) throws Exception {
        SignatureData data = SignatureMapper.INSTANCE.toSignatureData(request);
        String rawData = data.toEncodedString();
        byte[] signature = sign(rawData.getBytes());
        return rawData + "||SIG||" + Base64.getEncoder().encodeToString(signature);
    }

    // Vérifie les données signées et retourne le résultat (valide/invalide)
    public VerificationResult verifySignature(String signedData) {
        try {
            String[] parts = signedData.split("\\|\\|SIG\\|\\|");
            if (parts.length != 2) {
                return new VerificationResult(false, "Format invalide");
            }

            String rawData = parts[0];
            byte[] signature = Base64.getDecoder().decode(parts[1]);

            SignatureData data = SignatureData.fromString(rawData);

            // Vérification de la date d’expiration
            if (data.isExpired()) {
                return new VerificationResult(false, "Signature expirée");
            }

            // Vérification cryptographique
            boolean isValid = verify(rawData.getBytes(), signature);

            if (isValid) {
                return new VerificationResult(true, "Signature valide", toResponseDTO(data));
            } else {
                return new VerificationResult(false, "Signature invalide");
            }

        } catch (Exception e) {
            return new VerificationResult(false, "Erreur: " + e.getMessage());
        }
    }

    // Méthode de signature
    private byte[] sign(byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        return sig.sign();
    }

    // Méthode de vérification
    private boolean verify(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }

    private SignatureResponse toResponseDTO(SignatureData data) {
        return SignatureMapper.INSTANCE.toSignatureResponse(data);
    }

    // Classe interne contenant le résultat de la vérification
    public static class VerificationResult {
        private final boolean valid;
        private final String message;
        private final SignatureResponse data;

        public VerificationResult(boolean valid, String message) {
            this(valid, message, null);
        }

        public VerificationResult(boolean valid, String message, SignatureResponse data) {
            this.valid = valid;
            this.message = message;
            this.data = data;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }

        public SignatureResponse getData() {
            return data;
        }
    }
}

