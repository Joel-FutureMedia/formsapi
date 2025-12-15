package com.example.simplyfound.services;

import com.example.simplyfound.models.OpeningHours;
import com.example.simplyfound.repositories.OpeningHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpeningHoursService {
    @Autowired
    private OpeningHoursRepository openingHoursRepository;

    public OpeningHours save(OpeningHours openingHours) {
        return openingHoursRepository.save(openingHours);
    }

    public List<OpeningHours> findAll() {
        return openingHoursRepository.findAll();
    }

    public Optional<OpeningHours> findById(Long id) {
        return openingHoursRepository.findById(id);
    }

    public OpeningHours update(Long id, OpeningHours openingHours) {
        OpeningHours existing = openingHoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OpeningHours not found"));
        existing.setOpeningHours(openingHours.getOpeningHours());
        return openingHoursRepository.save(existing);
    }

    public void deleteById(Long id) {
        openingHoursRepository.deleteById(id);
    }
}

