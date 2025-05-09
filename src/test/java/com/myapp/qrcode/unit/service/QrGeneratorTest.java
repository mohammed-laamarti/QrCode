package com.myapp.qrcode.unit.service;

import com.myapp.qrcode.service.QrGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QrGeneratorTest {
    @Test
    void generateQRBytes_ShouldReturnNonEmptyArray() throws Exception {
        byte[] qrCode = QrGenerator.generateQRBytes("TestData");
        assertTrue(qrCode.length > 0);
    }

    @Test
    void generateQRBytes_ShouldThrowOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            QrGenerator.generateQRBytes(null);
        });
    }
}
