package com.myapp.qrcode.unit.service;

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
        SignatureData data = new SignatureData(
                "Martin",
                "Pierre",
                LocalDate.now().plusDays(30)
        );

        String signedData = signatureService.signData(data);
        SignatureService.VerificationResult result = signatureService.verifySignature(signedData);

        assertTrue(result.isValid());  // Teste le booléen isValid()
        assertEquals("Signature valide", result.getMessage());
    }

    @Test
    void testRejectExpiredSignature() throws Exception {
        SignatureData expiredData = new SignatureData(
                "Martin",
                "Pierre",
                LocalDate.now().minusDays(1) // Date expirée
        );

        String signedData = signatureService.signData(expiredData);
        SignatureService.VerificationResult result = signatureService.verifySignature(signedData);

        assertFalse(result.isValid());
        assertEquals("Signature expirée", result.getMessage());
    }

    @Test
    void testRejectTamperedData() throws Exception {
        SignatureData data = new SignatureData(
                "Martin",
                "Pierre",
                LocalDate.now().plusDays(30)
        );

        String signedData = signatureService.signData(data);
        String tamperedData = signedData.replace("Martin", "Robert");

        SignatureService.VerificationResult result = signatureService.verifySignature(tamperedData);
        assertFalse(result.isValid());
    }
}
