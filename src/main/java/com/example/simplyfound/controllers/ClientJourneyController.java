package com.example.simplyfound.controllers;

import com.example.simplyfound.models.ClientJourney;
import com.example.simplyfound.services.ClientJourneyService;
import com.example.simplyfound.services.EmailService;
import com.example.simplyfound.services.PromptPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client-journeys")
@CrossOrigin(origins = "*")
public class ClientJourneyController {
    @Autowired
    private ClientJourneyService clientJourneyService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PromptPdfService promptPdfService;

    @Value("${upload.path.logos}")
    private String logoUploadPath;

    @Value("${upload.path.images}")
    private String imageUploadPath;

    @PostMapping("/submit")
    public ResponseEntity<?> submitForm(
            @RequestParam("fullName") String fullName,
            @RequestParam("companyName") String companyName,
            @RequestParam("email") String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam("businessLocation") String businessLocation,
            @RequestParam("aboutInformation") String aboutInformation,
            @RequestParam(value = "industryId", required = false) Long industryId,
            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
            @RequestParam("businessPhone") String businessPhone,
            @RequestParam("businessEmail") String businessEmail,
            @RequestParam(value = "openingDaysId", required = false) Long openingDaysId,
            @RequestParam(value = "customHours", required = false) String customHours,
            @RequestParam(value = "serviceNames", required = false) String[] serviceNames,
            @RequestParam(value = "serviceDescriptions", required = false) String[] serviceDescriptions,
            @RequestParam(value = "primaryColor", required = false) String primaryColor,
            @RequestParam(value = "secondaryColor", required = false) String secondaryColor,
            @RequestParam(value = "layoutStyleId", required = false) Long layoutStyleId,
            @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles,
            @RequestParam(value = "facebookUrl", required = false) String facebookUrl,
            @RequestParam(value = "instagramUrl", required = false) String instagramUrl,
            @RequestParam(value = "googleBusinessUrl", required = false) String googleBusinessUrl,
            @RequestParam(value = "tiktokUrl", required = false) String tiktokUrl,
            @RequestParam(value = "testimonials", required = false) String testimonials,
            @RequestParam("primaryCta") String primaryCta,
            @RequestParam("ctaLinkOrPhone") String ctaLinkOrPhone,
            @RequestParam(value = "secondaryAction", required = false) String secondaryAction) {

        try {
            ClientJourney saved = clientJourneyService.submitForm(
                    fullName, companyName, email, phoneNumber, businessLocation,
                    aboutInformation, industryId, logoFile, businessPhone, businessEmail,
                    openingDaysId, customHours, serviceNames, serviceDescriptions,
                    primaryColor, secondaryColor, layoutStyleId, imageFiles, facebookUrl, instagramUrl,
                    googleBusinessUrl, tiktokUrl, testimonials, primaryCta, ctaLinkOrPhone, secondaryAction);

            // Send emails asynchronously - don't wait for them to complete
            // This allows the response to return immediately
            emailService.sendConfirmationEmail(email, fullName)
                    .exceptionally(ex -> {
                        System.err.println("Failed to send confirmation email: " + ex.getMessage());
                        return false;
                    });

            emailService.sendAdminNotificationEmail(saved.getId(), fullName, companyName, email)
                    .exceptionally(ex -> {
                        System.err.println("Failed to send admin notification email: " + ex.getMessage());
                        return false;
                    });

            // Return response immediately without waiting for emails
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing form: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading files: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping
    public List<ClientJourney> findAll() {
        return clientJourneyService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ClientJourney> findById(@PathVariable Long id) {
        return clientJourneyService.findById(id);
    }

    @PutMapping("/{id}")
    public ClientJourney update(@PathVariable Long id, @RequestBody ClientJourney clientJourney) {
        return clientJourneyService.update(id, clientJourney);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        clientJourneyService.deleteById(id);
    }

    @GetMapping("/{id}/download-prompt")
    public ResponseEntity<byte[]> downloadPromptPdf(@PathVariable Long id) {
        try {
            byte[] pdfBytes = promptPdfService.generatePromptPdf(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "website-prompt-" + id + ".pdf");
            headers.setContentLength(pdfBytes.length);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/logos/view/{fileName}")
    public ResponseEntity<Resource> viewLogo(@PathVariable String fileName) {
        try {
            String absolutePath = logoUploadPath.startsWith("/") || logoUploadPath.contains(":") 
                ? logoUploadPath 
                : System.getProperty("user.dir") + "/" + logoUploadPath;
            Path uploadPath = Paths.get(absolutePath).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();
            
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }
            
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/images/view/{fileName}")
    public ResponseEntity<Resource> viewImage(@PathVariable String fileName) {
        try {
            String absolutePath = imageUploadPath.startsWith("/") || imageUploadPath.contains(":") 
                ? imageUploadPath 
                : System.getProperty("user.dir") + "/" + imageUploadPath;
            Path uploadPath = Paths.get(absolutePath).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();
            
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }
            
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

