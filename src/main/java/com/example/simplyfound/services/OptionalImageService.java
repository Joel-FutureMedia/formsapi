package com.example.simplyfound.services;

import com.example.simplyfound.models.ClientJourney;
import com.example.simplyfound.models.OptionalImage;
import com.example.simplyfound.repositories.ClientJourneyRepository;
import com.example.simplyfound.repositories.OptionalImageRepository;
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
public class OptionalImageService {
    @Autowired
    private OptionalImageRepository optionalImageRepository;

    @Autowired
    private ClientJourneyRepository clientJourneyRepository;

    @Value("${upload.path.images}")
    private String imageUploadPath;

    private String getAbsolutePath(String relativePath) {
        if (relativePath.startsWith("/") || relativePath.contains(":")) {
            return relativePath;
        }
        return System.getProperty("user.dir") + "/" + relativePath;
    }

    public OptionalImage save(OptionalImage optionalImage) {
        return optionalImageRepository.save(optionalImage);
    }

    public List<OptionalImage> findAll() {
        return optionalImageRepository.findAll();
    }

    public Optional<OptionalImage> findById(Long id) {
        return optionalImageRepository.findById(id);
    }

    public List<OptionalImage> findByClientJourneyId(Long clientJourneyId) {
        return optionalImageRepository.findByClientJourneyId(clientJourneyId);
    }

    public OptionalImage update(Long id, OptionalImage optionalImage) {
        OptionalImage existing = optionalImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OptionalImage not found"));
        existing.setFileName(optionalImage.getFileName());
        existing.setFileUrl(optionalImage.getFileUrl());
        existing.setFileType(optionalImage.getFileType());
        return optionalImageRepository.save(existing);
    }

    public void deleteById(Long id) {
        optionalImageRepository.deleteById(id);
    }

    public OptionalImage uploadImage(MultipartFile file, Long clientJourneyId) throws IOException {
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        ClientJourney clientJourney = clientJourneyRepository.findById(clientJourneyId)
                .orElseThrow(() -> new RuntimeException("ClientJourney not found"));

        String absolutePath = getAbsolutePath(imageUploadPath);
        File uploadDir = new File(absolutePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                + "_" + System.currentTimeMillis()
                + originalFileName.substring(originalFileName.lastIndexOf('.'));
        Path filePath = Paths.get(absolutePath).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        OptionalImage optionalImage = new OptionalImage();
        optionalImage.setFileName(fileName);
        optionalImage.setFileUrl("/api/client-journeys/images/view/" + fileName);
        optionalImage.setFileType(file.getContentType());
        optionalImage.setClientJourney(clientJourney);

        return optionalImageRepository.save(optionalImage);
    }

    public List<OptionalImage> uploadMultipleImages(MultipartFile[] files, Long clientJourneyId) throws IOException {
        List<OptionalImage> uploadedImages = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                OptionalImage uploaded = uploadImage(file, clientJourneyId);
                uploadedImages.add(uploaded);
            }
        }

        return uploadedImages;
    }
}

