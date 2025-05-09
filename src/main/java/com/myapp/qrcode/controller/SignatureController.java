package com.myapp.qrcode.controller;

import com.myapp.qrcode.model.SignatureData;
import com.myapp.qrcode.service.QrGenerator;
import com.myapp.qrcode.service.SignatureService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/signature")
public class SignatureController {

    private final SignatureService signatureService;

    public SignatureController() throws Exception {
        this.signatureService = new SignatureService();
    }

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String expirationDate) throws Exception {

        SignatureData data = new SignatureData(
                nom,
                prenom,
                LocalDate.parse(expirationDate)
        );

        String signedData = signatureService.signData(data);
        byte[] qrCodeBytes = QrGenerator.generateQRBytes(signedData);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestBody String qrContent) {
        SignatureService.VerificationResult result = signatureService.verifySignature(qrContent);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", result.isValid());
        response.put("message", result.getMessage());

        if (result.isValid()) {
            response.put("data", Map.of(
                    "nom", result.getData().getNom(),
                    "prenom", result.getData().getPrenom(),
                    "dateCreation", result.getData().getDateCreation(),
                    "dateExpiration", result.getData().getDateExpiration()
            ));
        }

        return ResponseEntity.ok(response);
    }
}
