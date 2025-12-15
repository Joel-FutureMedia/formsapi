package com.example.simplyfound.controllers;

import com.example.simplyfound.models.OptionalImage;
import com.example.simplyfound.services.OptionalImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/optional-images")
public class OptionalImageController {
    @Autowired
    private OptionalImageService optionalImageService;

    @PostMapping("/upload")
    public ResponseEntity<OptionalImage> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientJourneyId") Long clientJourneyId) {
        try {
            OptionalImage uploaded = optionalImageService.uploadImage(file, clientJourneyId);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<OptionalImage>> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("clientJourneyId") Long clientJourneyId) {
        try {
            List<OptionalImage> uploaded = optionalImageService.uploadMultipleImages(files, clientJourneyId);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public OptionalImage create(@RequestBody OptionalImage optionalImage) {
        return optionalImageService.save(optionalImage);
    }

    @GetMapping
    public List<OptionalImage> findAll() {
        return optionalImageService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<OptionalImage> findById(@PathVariable Long id) {
        return optionalImageService.findById(id);
    }

    @GetMapping("/client-journey/{clientJourneyId}")
    public List<OptionalImage> findByClientJourneyId(@PathVariable Long clientJourneyId) {
        return optionalImageService.findByClientJourneyId(clientJourneyId);
    }

    @PutMapping("/{id}")
    public OptionalImage update(@PathVariable Long id, @RequestBody OptionalImage optionalImage) {
        return optionalImageService.update(id, optionalImage);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        optionalImageService.deleteById(id);
    }
}

