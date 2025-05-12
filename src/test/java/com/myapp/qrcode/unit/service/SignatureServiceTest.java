package com.myapp.qrcode.unit.service;

import com.myapp.qrcode.dto.CreateSignatureRequest;
import com.myapp.qrcode.model.SignatureData;
import com.myapp.qrcode.service.SignatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



public class SignatureServiceTest {

    private SignatureService signatureService;

    @BeforeEach
    void setUp() throws Exception {
        signatureService = new SignatureService();
    }

    @Test
    void testVerifyValidSignature() throws Exception {
        CreateSignatureRequest data = new CreateSignatureRequest("Martin", "Pierre");


        String signedData = signatureService.signData(data);
        SignatureService.VerificationResult result = signatureService.verifySignature(signedData);

        assertTrue(result.isValid(), "La signature doit être valide.");
        assertEquals("Signature valide", result.getMessage());
    }

    @Test
    void testRejectExpiredSignature() throws Exception {
        CreateSignatureRequest expiredData = new CreateSignatureRequest("Martin", "Pierre");


        String signedData = signatureService.signData(expiredData);
        SignatureService.VerificationResult result = signatureService.verifySignature(signedData);

        assertFalse(result.isValid(), "La signature expirée doit être rejetée.");
        assertEquals("Signature expirée", result.getMessage());
    }

    @Test
    void testRejectTamperedData() throws Exception {

        CreateSignatureRequest data  = new CreateSignatureRequest("Martin", "Pierre");
        String signedData = signatureService.signData(data);

        // Modifier les données signées (ex : changer un nom)
        String tamperedData = signedData.replace("Martin", "Robert");

        SignatureService.VerificationResult result = signatureService.verifySignature(tamperedData);

        assertFalse(result.isValid(), "Une signature falsifiée doit être invalide.");
    }
}