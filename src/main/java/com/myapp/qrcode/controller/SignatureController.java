package com.myapp.qrcode.controller;

import com.myapp.qrcode.dto.CreateSignatureRequest;
import com.myapp.qrcode.dto.SignatureResponse;
import com.myapp.qrcode.model.SignatureData;
import com.myapp.qrcode.service.QrGenerator;
import com.myapp.qrcode.service.SignatureService;
import com.myapp.qrcode.validation.CreateSignatureRequestValidator;
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

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(
            @RequestParam String nom,
            @RequestParam String prenom) throws Exception {

        CreateSignatureRequest request = new CreateSignatureRequest(nom, prenom);

        // ✅ Validation séparée
        CreateSignatureRequestValidator.validate(request);

        // Construction des données et signature
        CreateSignatureRequest data = new CreateSignatureRequest(request.getNom(), request.getPrenom());
        String signedData = signatureService.signData(data);
        byte[] qrCodeBytes = QrGenerator.generateQRBytes(signedData);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);
    }


    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestBody String qrContent) {
        // Vérification de la signature à partir du contenu du QR code
        SignatureService.VerificationResult result = signatureService.verifySignature(qrContent);

        // Préparer la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("valid", result.isValid());
        response.put("message", result.getMessage());

        // Si la signature est valide, ajouter les données dans la réponse
        if (result.isValid()) {
            SignatureResponse data = result.getData();  // Récupère SignatureResponse

            response.put("data", Map.of(
                    "nom", data.getNom(),
                    "prenom", data.getPrenom(),
                    "dateCreation", data.getDateCreation(),
                    "dateExpiration", data.getDateExpiration()
            ));
        }

        return ResponseEntity.ok(response);
    }
}


