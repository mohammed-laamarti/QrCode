package com.myapp.qrcode.unit.model;

import com.myapp.qrcode.model.SignatureData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SignatureDataTest {

    @Test
    void testIsExpired() {
        // Données expirées : dateExpiration dans le passé
        SignatureData expiredData = new SignatureData("Test", "User");
        expiredData.setDateCreation(LocalDate.now().minusMonths(7));
        expiredData.setDateExpiration(LocalDate.now().minusDays(1));

        assertTrue(expiredData.isExpired(), "La signature devrait être expirée.");
    }

    @Test
    void testToEncodedString() {
        // Données connues
        SignatureData data = new SignatureData("Dupont", "Jean");
        data.setDateCreation(LocalDate.of(2024, 1, 1));
        data.setDateExpiration(LocalDate.of(2024, 7, 1));

        String encoded = data.toEncodedString();
        assertEquals("Dupont|Jean|2024-01-01|2024-07-01", encoded);
    }
}
