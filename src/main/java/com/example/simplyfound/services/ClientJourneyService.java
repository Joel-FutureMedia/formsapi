package com.example.simplyfound.services;

import com.example.simplyfound.models.*;
import com.example.simplyfound.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientJourneyService {
    @Autowired
    private ClientJourneyRepository clientJourneyRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ColorPaletteRepository colorPaletteRepository;

    @Autowired
    private LayoutStyleRepository layoutStyleRepository;

    @Autowired
    private OpeningDaysRepository openingDaysRepository;

    @Autowired
    private ServiceOrProductRepository serviceOrProductRepository;

    @Autowired
    private OptionalImageRepository optionalImageRepository;

    @Value("${upload.path.logos}")
    private String logoUploadPath;

    @Value("${upload.path.images}")
    private String imageUploadPath;

    private String getAbsolutePath(String relativePath) {
        if (relativePath.startsWith("/") || relativePath.contains(":")) {
            return relativePath;
        }
        return System.getProperty("user.dir") + "/" + relativePath;
    }

    public ClientJourney save(ClientJourney clientJourney) {
        return clientJourneyRepository.save(clientJourney);
    }

    public List<ClientJourney> findAll() {
        return clientJourneyRepository.findAll();
    }

    public Optional<ClientJourney> findById(Long id) {
        return clientJourneyRepository.findById(id);
    }

    public ClientJourney update(Long id, ClientJourney clientJourney) {
        ClientJourney existing = clientJourneyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientJourney not found"));
        
        existing.setFullName(clientJourney.getFullName());
        existing.setCompanyName(clientJourney.getCompanyName());
        existing.setEmail(clientJourney.getEmail());
        existing.setPhoneNumber(clientJourney.getPhoneNumber());
        existing.setBusinessLocation(clientJourney.getBusinessLocation());
        existing.setAboutInformation(clientJourney.getAboutInformation());
        existing.setBusinessPhone(clientJourney.getBusinessPhone());
        existing.setBusinessEmail(clientJourney.getBusinessEmail());
        existing.setCustomHours(clientJourney.getCustomHours());
        existing.setFacebookUrl(clientJourney.getFacebookUrl());
        existing.setInstagramUrl(clientJourney.getInstagramUrl());
        existing.setGoogleBusinessUrl(clientJourney.getGoogleBusinessUrl());
        existing.setTiktokUrl(clientJourney.getTiktokUrl());
        existing.setTestimonials(clientJourney.getTestimonials());
        existing.setPrimaryCta(clientJourney.getPrimaryCta());
        existing.setCtaLinkOrPhone(clientJourney.getCtaLinkOrPhone());
        existing.setSecondaryAction(clientJourney.getSecondaryAction());
        
        if (clientJourney.getIndustry() != null) {
            existing.setIndustry(clientJourney.getIndustry());
        }
        if (clientJourney.getPrimaryColor() != null) {
            existing.setPrimaryColor(clientJourney.getPrimaryColor());
        }
        if (clientJourney.getSecondaryColor() != null) {
            existing.setSecondaryColor(clientJourney.getSecondaryColor());
        }
        if (clientJourney.getLayoutStyle() != null) {
            existing.setLayoutStyle(clientJourney.getLayoutStyle());
        }
        if (clientJourney.getOpeningDays() != null) {
            existing.setOpeningDays(clientJourney.getOpeningDays());
        }
        
        return clientJourneyRepository.save(existing);
    }

    public void deleteById(Long id) {
        clientJourneyRepository.deleteById(id);
    }

    public String uploadLogo(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Logo file is empty");
        }
        
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }

        String absolutePath = getAbsolutePath(logoUploadPath);
        File uploadDir = new File(absolutePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        String fileName;
        if (lastDotIndex > 0) {
            fileName = originalFileName.substring(0, lastDotIndex)
                    + "_" + System.currentTimeMillis()
                    + originalFileName.substring(lastDotIndex);
        } else {
            fileName = originalFileName + "_" + System.currentTimeMillis();
        }
        
        Path filePath = Paths.get(absolutePath).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }
        
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }

        String absolutePath = getAbsolutePath(imageUploadPath);
        File uploadDir = new File(absolutePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        String fileName;
        if (lastDotIndex > 0) {
            fileName = originalFileName.substring(0, lastDotIndex)
                    + "_" + System.currentTimeMillis()
                    + originalFileName.substring(lastDotIndex);
        } else {
            fileName = originalFileName + "_" + System.currentTimeMillis();
        }
        
        Path filePath = Paths.get(absolutePath).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    public ClientJourney submitForm(
            String fullName, String companyName, String email, String phoneNumber,
            String businessLocation, String aboutInformation, Long industryId,
            MultipartFile logoFile, String businessPhone, String businessEmail,
            Long openingDaysId, String customHours, String[] serviceNames,
            String[] serviceDescriptions, String primaryColor, String secondaryColor, Long layoutStyleId,
            MultipartFile[] imageFiles, String facebookUrl, String instagramUrl,
            String googleBusinessUrl, String tiktokUrl, String testimonials,
            String primaryCta, String ctaLinkOrPhone, String secondaryAction) throws IOException {

        ClientJourney clientJourney = new ClientJourney();
        clientJourney.setFullName(fullName);
        clientJourney.setCompanyName(companyName);
        clientJourney.setEmail(email);
        clientJourney.setPhoneNumber(phoneNumber);
        clientJourney.setBusinessLocation(businessLocation);
        clientJourney.setAboutInformation(aboutInformation);
        clientJourney.setBusinessPhone(businessPhone);
        clientJourney.setBusinessEmail(businessEmail);
        clientJourney.setCustomHours(customHours);
        clientJourney.setFacebookUrl(facebookUrl);
        clientJourney.setInstagramUrl(instagramUrl);
        clientJourney.setGoogleBusinessUrl(googleBusinessUrl);
        clientJourney.setTiktokUrl(tiktokUrl);
        clientJourney.setTestimonials(testimonials);
        clientJourney.setPrimaryCta(primaryCta);
        clientJourney.setCtaLinkOrPhone(ctaLinkOrPhone);
        clientJourney.setSecondaryAction(secondaryAction);

        if (industryId != null) {
            Industry industry = industryRepository.findById(industryId)
                    .orElseThrow(() -> new RuntimeException("Industry not found"));
            clientJourney.setIndustry(industry);
        }

        if (openingDaysId != null) {
            OpeningDays openingDays = openingDaysRepository.findById(openingDaysId)
                    .orElseThrow(() -> new RuntimeException("OpeningDays not found"));
            clientJourney.setOpeningDays(openingDays);
        }

        // Store color codes directly
        if (primaryColor != null && !primaryColor.trim().isEmpty()) {
            clientJourney.setPrimaryColor(primaryColor.trim());
        }
        if (secondaryColor != null && !secondaryColor.trim().isEmpty()) {
            clientJourney.setSecondaryColor(secondaryColor.trim());
        }

        if (layoutStyleId != null) {
            Optional<LayoutStyle> layoutStyleOpt = layoutStyleRepository.findById(layoutStyleId);
            if (layoutStyleOpt.isPresent()) {
                clientJourney.setLayoutStyle(layoutStyleOpt.get());
            }
            // If layoutStyleId doesn't exist in DB, skip it gracefully
        }

        if (logoFile != null && !logoFile.isEmpty()) {
            String logoFileName = uploadLogo(logoFile);
            clientJourney.setLogoFileName(logoFileName);
            clientJourney.setLogoFileUrl("/api/client-journeys/logos/view/" + logoFileName);
            clientJourney.setLogoFileType(logoFile.getContentType());
        }

        ClientJourney saved = clientJourneyRepository.save(clientJourney);

        if (serviceNames != null && serviceNames.length > 0) {
            List<ServiceOrProduct> services = new ArrayList<>();
            int maxLength = serviceNames.length;
            // Handle case where descriptions array might be shorter or null
            int descLength = (serviceDescriptions != null) ? serviceDescriptions.length : 0;
            
            for (int i = 0; i < maxLength; i++) {
                if (serviceNames[i] != null && !serviceNames[i].trim().isEmpty()) {
                    ServiceOrProduct service = new ServiceOrProduct();
                    service.setName(serviceNames[i].trim());
                    // Description can be empty, so we allow it
                    if (i < descLength && serviceDescriptions[i] != null) {
                        service.setDescription(serviceDescriptions[i].trim());
                    } else {
                        service.setDescription("");
                    }
                    service.setClientJourney(saved);
                    services.add(service);
                }
            }
            if (!services.isEmpty()) {
                serviceOrProductRepository.saveAll(services);
            }
        }

        if (imageFiles != null && imageFiles.length > 0) {
            List<OptionalImage> images = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                if (imageFile != null && !imageFile.isEmpty()) {
                    String imageFileName = uploadImage(imageFile);
                    OptionalImage image = new OptionalImage();
                    image.setFileName(imageFileName);
                    image.setFileUrl("/api/client-journeys/images/view/" + imageFileName);
                    image.setFileType(imageFile.getContentType());
                    image.setClientJourney(saved);
                    images.add(image);
                }
            }
            if (!images.isEmpty()) {
                optionalImageRepository.saveAll(images);
            }
        }

        // Return the saved entity directly - no need for another database query
        return saved;
    }
}

