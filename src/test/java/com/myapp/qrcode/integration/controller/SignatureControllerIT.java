package com.myapp.qrcode.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
class SignatureControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateQR_ShouldReturnPngImage() throws Exception {
        mockMvc.perform(get("/api/signature/generate")
                        .param("nom", "Dupont")
                        .param("prenom", "Jean")
                        .param("expirationDate", "2025-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }
}
