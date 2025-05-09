package com.myapp.qrcode.unit.model;

import com.myapp.qrcode.model.SignatureData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignatureDataTest {
    @Test
    void testIsExpired() {
        SignatureData expiredData = new SignatureData(
                "Test", "User", LocalDate.now().minusDays(1)
        );
        assertTrue(expiredData.isExpired());
    }

    @Test
    void testToEncodedString() {
        SignatureData data = new SignatureData(
                "Dupont", "Jean", LocalDate.parse("2025-12-31")
        );
        String expected = "Dupont|Jean|" + LocalDate.now() + "|2025-12-31";
        assertTrue(data.toEncodedString().startsWith("Dupont|Jean"));
    }
}
