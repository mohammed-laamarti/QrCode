package com.myapp.qrcode.service;

import com.myapp.qrcode.model.SignatureData;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Base64;
@Service
public class SignatureService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public SignatureService() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA")
                .generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public String signData(SignatureData data) throws Exception {
        String rawData = data.toEncodedString();
        byte[] signature = sign(rawData.getBytes());
        return rawData + "||SIG||" + Base64.getEncoder().encodeToString(signature);
    }

    public VerificationResult verifySignature(String signedData) {
        try {
            String[] parts = signedData.split("\\|\\|SIG\\|\\|");
            if (parts.length != 2) {
                return new VerificationResult(false, "Format invalide");
            }

            // Extraction des données
            SignatureData data = SignatureData.fromString(parts[0]);
            byte[] signature = Base64.getDecoder().decode(parts[1]);

            // Vérification de la date
            if (data.isExpired()) {
                return new VerificationResult(false, "Signature expirée");
            }

            // Vérification cryptographique
            boolean isValid = verify(parts[0].getBytes(), signature);

            return isValid
                    ? new VerificationResult(true, "Signature valide", data)
                    : new VerificationResult(false, "Signature invalide");

        } catch (Exception e) {
            return new VerificationResult(false, "Erreur: " + e.getMessage());
        }
    }

    private byte[] sign(byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        return sig.sign();
    }

    private boolean verify(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }
    public static class VerificationResult {
        private final boolean valid;
        private final String message;
        private final SignatureData data;

        public VerificationResult(boolean valid, String message) {
            this(valid, message, null);
        }

        public VerificationResult(boolean valid, String message, SignatureData data) {
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

        public SignatureData getData() {
            return data;
        }
    }
}
