package com.myapp.qrcode.integration.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.qrcode.model.SignatureData;
import com.myapp.qrcode.service.SignatureService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

@WebMvcTest
class SignatureControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SignatureService signatureService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SignatureService signatureService() {
            return org.mockito.Mockito.mock(SignatureService.class);
        }
    }

    @Test
    void verify_ShouldReturnValidForGoodSignature() throws Exception {
        // Nouvelle syntaxe Mockito
        when(signatureService.verifySignature(anyString()))
                .thenReturn(new SignatureService.VerificationResult(
                        true,
                        "Valid",
                        new SignatureData("Test", "User", LocalDate.now().plusDays(30))
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signature/verify")
                        .contentType("text/plain")
                        .content("valid-data||SIG||signature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));
    }
}
